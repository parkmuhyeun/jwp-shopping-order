package cart.dto;

public class OrderProductRequest {
    private Long id;
    private int quantity;

    public OrderProductRequest() {
    }

    public OrderProductRequest(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }
}
