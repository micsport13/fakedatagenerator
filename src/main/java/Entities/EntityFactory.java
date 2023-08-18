package Entities;

public class EntityFactory {

    public Entity EntityFactory(String entityType) {
        switch (entityType) {
            case "Invoice" -> {
                return InvoiceLineItem.generate();
            }
            case "InvoiceLineItem" {
                InvoiceLineItem.generate();
            }
        }
    }
}
