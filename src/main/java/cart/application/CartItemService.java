package cart.application;

import cart.dao.CartItemDao;
import cart.dao.ProductDao;
import cart.domain.CartItem;
import cart.domain.Member;
import cart.dto.CartItemQuantityUpdateRequest;
import cart.dto.CartItemRequest;
import cart.dto.OrderProductRequest;
import cart.dto.CartItemResponse;
import cart.dto.RemoveCartItemsRequest;
import cart.exception.BusinessException;
import java.util.Optional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService {
    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    public CartItemService(ProductDao productDao, CartItemDao cartItemDao) {
        this.productDao = productDao;
        this.cartItemDao = cartItemDao;
    }

    public List<CartItemResponse> findByMember(Member member) {
        List<CartItem> cartItems = cartItemDao.findByMemberId(member.getId());
        return cartItems.stream().map(CartItemResponse::of).collect(Collectors.toList());
    }

    public Long add(Member member, CartItemRequest cartItemRequest) {
        final Optional<CartItem> optionalCartItem = cartItemDao.findByMemberIdAndProductId(member.getId(),
            cartItemRequest.getProductId());
        if (optionalCartItem.isPresent()) {
            final CartItem cartItem = optionalCartItem.get();
            final CartItem updatedCartItem = cartItem.addQuantity(cartItemRequest.getQuantity());
            cartItemDao.updateQuantity(updatedCartItem);
            return cartItem.getId();
        }

        return cartItemDao.save(new CartItem(member,
            productDao.getProductById(cartItemRequest.getProductId())
                .orElseThrow(() -> new BusinessException("찾는 상품이 존재하지 않습니다."))));
    }

    public void updateQuantity(Member member, Long id, CartItemQuantityUpdateRequest request) {
        CartItem cartItem = cartItemDao.findById(id);
        cartItem.checkOwner(member);

        if (request.getQuantity() == 0) {
            cartItemDao.deleteById(id);
            return;
        }

        cartItem.changeQuantity(request.getQuantity());
        cartItemDao.updateQuantity(cartItem);
    }

    public void remove(Member member, Long id) {
        CartItem cartItem = cartItemDao.findById(id);
        cartItem.checkOwner(member);

        cartItemDao.deleteById(id);
    }

    public void remove(final Member member, final RemoveCartItemsRequest request) {
        final List<CartItem> cartItems = cartItemDao.findAllByIds(request.getCartItemIds());
        for (final CartItem cartItem : cartItems) {
            cartItem.checkOwner(member);
        }
        cartItemDao.deleteAll(cartItems);
    }
}
