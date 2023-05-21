package com.example.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import com.example.model.Book;
import com.example.model.Customer;
import com.example.model.Karyawan;
import com.example.model.Order;
import com.example.util.DBUtil;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class MainController implements Initializable {

    @FXML
    private Text txtGrandTotal;
    private Float grandTotal;


    @FXML
    private TextField txfIdSewa;
    @FXML
    private TextField txfIdCustomer;
    @FXML
    private TextField txfIdKaryawan;
    @FXML
    private TextField txfViewCustomerName;
    @FXML
    private TextField txfViewKaryawanName;

    @FXML
    private Text txtViewCustomerNameError;
    @FXML
    private Text txtViewKaryawanNameError;

    @FXML
    private Button btnViewCustomerName;
    @FXML
    private Button btnViewKaryawanName;

    @FXML
    private ComboBox<Book> cmbKodeBuku;
    @FXML
    private TextField txfNamaBuku;
    @FXML
    private TextField txfTglPeminjaman;
    @FXML
    private TextField txfTglPengembalian;
    @FXML
    private TextField txfHarga;
    @FXML
    private Button btnTambahItem;
    @FXML
    private Button btnHapusItem;
    
    @FXML
    private TableView<Order> tbvDetailOrder;

    @FXML
    private TableColumn<Order, String> tbcKodeBuku;
    @FXML
    private TableColumn<Order, String> tbcNamaBuku;
    @FXML
    private TableColumn<Order, String> tbcTglPeminjaman;
    @FXML
    private TableColumn<Order, String> tbcTglPengembalian;
    @FXML
    private TableColumn<Order, String> tbcHarga;
    
    private Order selectedOrder;

    @FXML
    private TextField txfBayar;
    @FXML
    private TextField txfKembalian;

    @FXML
    private Button btnSimpan;
    @FXML
    private Button btnExit;

    public void viewCustomerName() {
        try {      
            txtViewCustomerNameError.setText("");

            Customer customer = findCustomerById(txfIdCustomer.getText());
            if(customer != null) {
                txfViewCustomerName.setText(customer.getNamaCustomer());
            } else {
                txfViewCustomerName.setText("");
                txtViewCustomerNameError.setText("Kode customer tidak ditemukan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void viewKaryawanName() {
        try {      
            txtViewKaryawanNameError.setText("");

            Karyawan karyawan = findKaryawanById(txfIdKaryawan.getText());
            if(karyawan != null) {
                txfViewKaryawanName.setText(karyawan.getNamaKaryawan());
            } else {
                txfViewKaryawanName.setText("");
                txtViewKaryawanNameError.setText("Kode karyawan tidak ditemukan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showAllBook() {
        try {      
            Connection con = null;
            ResultSet rs = null;
            PreparedStatement pstmt = null;

            con = DBUtil.getConnection();
            String sql = "SELECT * FROM books";
            pstmt = con.prepareStatement(sql);

            rs = pstmt.executeQuery();
            
            ObservableList<Book> items = cmbKodeBuku.getItems();
            while (rs.next()) {
                items.add(new Book(rs.getString("id_books"), rs.getString("nama_buku"), rs.getFloat("harga")));
            }

            cmbKodeBuku.setCellFactory(new Callback<ListView<Book>, ListCell<Book>>() {
                @Override
                public ListCell<Book> call(ListView<Book> param) {
                    return new ListCell<Book>() {
                        @Override
                        protected void updateItem(Book item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null && !empty) {
                                setText(item.getIdBooks());
                            } else {
                                setText(null);
                            }
                        }
                    };
                }
            });

            cmbKodeBuku.setConverter(new StringConverter<Book>() {
                @Override
                public String toString(Book book) {
                    return book != null ? book.getIdBooks() : null;
                }
                @Override
                public Book fromString(String string) {
                    return null;
                } 
            });

            cmbKodeBuku.valueProperty().addListener((observable, oldvalue, newvalue) -> {
                if(newvalue != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    DecimalFormat decimalFormat = new DecimalFormat("#,###");
                    
                    txfNamaBuku.setText(newvalue.getNamaBuku());
                    txfTglPeminjaman.setText(sdf.format(new Date()));
                    txfTglPengembalian.setText(sdf.format(new Date().getTime() + (60000 * 60 * 24 * 7)));
                    txfHarga.setText(decimalFormat.format(newvalue.getHarga()));
    
                    selectedOrder = new Order(newvalue.getIdBooks(), newvalue.getNamaBuku(), sdf.format(new Date()), sdf.format(new Date().getTime() + (60000 * 60 * 24 * 7)), decimalFormat.format(newvalue.getHarga()));
                } else {
                    txfNamaBuku.setText("");
                    txfTglPeminjaman.setText("");
                    txfTglPengembalian.setText("");
                    txfHarga.setText("");

                    selectedOrder = null;
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void tambahBuku() {
        if(selectedOrder == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Harap pilih buku terlebih dahulu!");
            alert.showAndWait();
            return;
        }

        if(!validateTambahBuku(selectedOrder.getKodeBuku())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Buku sudah masuk dalah daftar order!");
            alert.showAndWait();
            return;
        }

        ObservableList<Order> data = FXCollections.observableArrayList(selectedOrder);

        tbvDetailOrder.getItems().addAll(data);

        calculateGrandTotal();
    }

    public void hapusItem() {
        for(int i = 0; i < tbvDetailOrder.getItems().size(); i++) {
            if(tbvDetailOrder.getItems().get(i).getKodeBuku().equals(selectedOrder.getKodeBuku())) {
                tbvDetailOrder.getItems().remove(i);
                continue;
            }
        }

        calculateGrandTotal();
    }

    public Boolean validateTambahBuku(String kodeBuku) {
        for(Order order : tbvDetailOrder.getItems()) {
            if(order.getKodeBuku().equals(kodeBuku)) {
                return false;
            }
        }

        return true;
    }

    public Customer findCustomerById(String idCustomer) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        con = DBUtil.getConnection();
        String sql = "SELECT * FROM customer WHERE id_customer = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, idCustomer);

        rs = pstmt.executeQuery();
        if(rs.first()){
            rs.beforeFirst();
            while (rs.next()) {
                return new Customer(rs.getString("id_customer"), rs.getString("name_customer"), rs.getDate("tanggal_lahir"), rs.getString("telepon"));
            }
        }
        return null;
    }

    public Karyawan findKaryawanById(String idKaryawan) throws SQLException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        con = DBUtil.getConnection();
        String sql = "SELECT * FROM karyawan WHERE id_karyawan = ?";
        pstmt = con.prepareStatement(sql);
        pstmt.setString(1, idKaryawan);

        rs = pstmt.executeQuery();
        if(rs.first()){
            rs.beforeFirst();
            while (rs.next()) {
                return new Karyawan(rs.getString("id_karyawan"), rs.getString("nama_karyawan"), rs.getString("telepon"));
            }
        }
        return null;
    }

    public void saveOrder() throws SQLException, ParseException {
        if (txfIdCustomer.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Harap isi input ID Customer terlebih dahulu!");
            alert.showAndWait();
            return;
        }
        
        Customer checkCustomer = findCustomerById(txfIdCustomer.getText());
        if(checkCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("ID Customer tidak ditemukan");
            alert.showAndWait();
            return;
        }

        if (txfBayar.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("Harap isi input bayar terlebih dahulu!");
            alert.showAndWait();
            return;
        }

        Float kembalian = Integer.valueOf(txfKembalian.getText()).floatValue();
        if(kembalian < 0 ) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Info");
            alert.setHeaderText("kurang bayar!!!!!");
            alert.showAndWait();
            return;
        }

        Connection con = null;
        PreparedStatement pstmt = null;

        con = DBUtil.getConnection();

        SimpleDateFormat oldSdf = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat newSdf = new SimpleDateFormat("yyyy-MM-dd");
        for(Order order : tbvDetailOrder.getItems()) {
            String sql = "INSERT INTO `sewa` (`customer_id`, `books_id`, `tgl_peminjaman`, `tgl_pengembalian`) VALUES (?, ?, ?, ?)";
            
            Date tglPeminjamanDate = oldSdf.parse(order.getTglPeminjaman());
            Date tglPengembalianDate = oldSdf.parse(order.getTglPengembalian());

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, checkCustomer.getIdCustomer());
            pstmt.setString(2, order.getKodeBuku());
            pstmt.setString(3, newSdf.format(tglPeminjamanDate));
            pstmt.setString(4, newSdf.format(tglPengembalianDate));


            pstmt.executeUpdate();
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("Order berhasil!");
        alert.showAndWait();
        clearAll();
    }

    public void clearAll() {
        txfBayar.setText("");
        txfKembalian.setText("");
        tbvDetailOrder.getItems().clear();

        cmbKodeBuku.valueProperty().setValue(null);
    }

    public void calculateGrandTotal() {
        DecimalFormat decimalFormat = new DecimalFormat("#,###");

        Float total = 0.0f;
        for(Order order : tbvDetailOrder.getItems()) {
            try {
                total += decimalFormat.parse(order.getHarga()).floatValue();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        txtGrandTotal.setText("Rp. " + decimalFormat.format(total));
        grandTotal = total;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        btnViewCustomerName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewCustomerName();;
            }
        });
        btnViewKaryawanName.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewKaryawanName();;
            }
        });

        // SHOW ALL BOOK
        tbcKodeBuku.setCellValueFactory(new PropertyValueFactory<Order, String>("kodeBuku"));
        tbcNamaBuku.setCellValueFactory(new PropertyValueFactory<Order, String>("namaBuku"));
        tbcTglPeminjaman.setCellValueFactory(new PropertyValueFactory<Order, String>("tglPeminjaman"));
        tbcTglPengembalian.setCellValueFactory(new PropertyValueFactory<Order, String>("tglPengembalian"));
        tbcHarga.setCellValueFactory(new PropertyValueFactory<Order, String>("harga"));
        showAllBook();

        btnTambahItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tambahBuku();
            }
        });
        btnHapusItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                hapusItem();
            }
        });

        btnSimpan.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    saveOrder();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        txfBayar.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txfBayar.setText(newValue.replaceAll("[^\\d]", ""));
            }

            DecimalFormat decimalFormat = new DecimalFormat("#,###");

            // CALCULATE KEMBALIAN
            try {
                if(!newValue.isEmpty()) {
                    Float bayar = decimalFormat.parse(newValue).floatValue();
                    Float kembalian = bayar - grandTotal;
    
                    txfKembalian.setText(String.valueOf(kembalian.intValue()));
                } else {
                    txfKembalian.setText("");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

}
