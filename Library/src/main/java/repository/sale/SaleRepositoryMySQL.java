package repository.sale;

import model.Sale;
import view.model.SaleDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleRepositoryMySQL implements SaleRepository{

    private final Connection connection;

    public SaleRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Sale sale) {
        String sql = "INSERT INTO sale (book_id, user_id, price, sold_date) VALUES (?,?,?,?)";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, sale.getBookId());
            statement.setLong(2, sale.getUserId());
            statement.setDouble(3, sale.getPrice());
            statement.setTimestamp(4, Timestamp.valueOf(sale.getSoldDate()));

            return statement.executeUpdate() > 0;


        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Sale> findAll() {
        return new ArrayList<>();
    }

    @Override
    public List<SaleDTO> findAllSalesDetails() {
        List<SaleDTO> sales = new ArrayList<>();
        String sql = "SELECT b.title, u.username, s.price, s.sold_date " +
                "FROM sale s " +
                "JOIN book b ON s.book_id = b.id " +
                "JOIN user u ON s.user_id = u.id " +
                "ORDER BY s.sold_date DESC";

        try
        {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while(rs.next())
            {
                sales.add(new SaleDTO(
                        rs.getString("title"),
                        rs.getString("username"),
                        rs.getDouble("price"),
                        rs.getTimestamp("sold_date").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }
}
