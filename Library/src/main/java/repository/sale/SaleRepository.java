package repository.sale;

import model.Sale;
import view.model.SaleDTO;

import java.util.List;

public interface SaleRepository {
    boolean save(Sale sale);
    List<Sale> findAll();
    List<SaleDTO> findAllSalesDetails();

}
