package com.woowahan.recipe.service;

import com.woowahan.recipe.domain.dto.cartDto.CartItemReq;
import com.woowahan.recipe.domain.entity.CartEntity;
import com.woowahan.recipe.domain.entity.CartItemEntity;
import com.woowahan.recipe.domain.entity.ItemEntity;
import com.woowahan.recipe.domain.entity.UserEntity;
import com.woowahan.recipe.exception.AppException;
import com.woowahan.recipe.exception.ErrorCode;
import com.woowahan.recipe.fixture.CartItemEntityFixture;
import com.woowahan.recipe.fixture.ItemEntityFixture;
import com.woowahan.recipe.fixture.TestInfoFixture;
import com.woowahan.recipe.fixture.UserEntityFixture;
import com.woowahan.recipe.repository.CartItemRepository;
import com.woowahan.recipe.repository.CartRepository;
import com.woowahan.recipe.repository.ItemRepository;
import com.woowahan.recipe.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartServiceTest {

    private CartService cartService;

    private CartRepository cartRepository = mock(CartRepository.class);
    private CartItemRepository cartItemRepository = mock(CartItemRepository.class);
    private UserRepository userRepository = mock(UserRepository.class);
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private OrderService orderService = mock(OrderService.class);
    private final String userName = TestInfoFixture.get().getUserName();
    private final String password = TestInfoFixture.get().getPassword();
    private final ItemEntity item = ItemEntityFixture.get();
    private final CartItemEntity cartItem = CartItemEntityFixture.get(userName, password);
    private CartItemReq cartItemReq;


    @BeforeEach
    void setUp() {
        cartService = new CartService(cartRepository, cartItemRepository, userRepository, itemRepository, orderService);
        cartItemReq = CartItemReq.builder()
                                    .cartItemId(item.getId())
                                    .cartItemCnt(3)
                                    .build();
    }

    @Nested
    @DisplayName("?????? ?????? ?????? ?????????")
    class ExceptionTest {
        @Test
        @DisplayName("????????? ???????????? ?????? ??????")
        void notFoundUser() {
            when(userRepository.findByUserName(userName)).thenReturn(Optional.empty());

            Throwable exception = assertThrows(AppException.class, () -> {
                throw new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage());
            });

            assertEquals("???????????? ?????? ???????????????", exception.getMessage());
        }

        @Test
        @DisplayName("???????????? ?????? ????????? ??????")
        void notFoundItem() {
            UserEntity user = UserEntityFixture.get(userName, password);

            when(itemRepository.findById(item.getId())).thenReturn(Optional.empty());

            // service ?????? ??????
            when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
            when(itemRepository.findById(item.getId())).thenReturn(Optional.empty());

            try {
                cartService.addCartItem(cartItemReq, userName);
            } catch (AppException e) {
                assertEquals("????????? ???????????? ????????????", e.getMessage());
            }
        }
    }

    @Nested
    @DisplayName("???????????? ????????? ?????? ?????????")
    class CreateCartItemTest {
        @Test
        @DisplayName("???????????? ????????? ?????? ??????")
        void createCartItem() {
            Long itemId = TestInfoFixture.get().getItemId();
            UserEntity user = mock(UserEntity.class);
            CartEntity cart = mock(CartEntity.class);

            when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
            when(itemRepository.findById(itemId)).thenReturn(Optional.of(item));
            when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

            cartService = spy(cartService);
            doNothing().when(cartService).addCartItem(cartItemReq, userName);
        }

        @Test
        @DisplayName("????????? ????????? ????????? ??????")
        void notEnoughStock() {
            UserEntity user = mock(UserEntity.class);
            CartEntity cart = mock(CartEntity.class);
            cartItemReq = CartItemReq.builder()
                                            .cartItemId(item.getId())
                                            .cartItemCnt(10)
                                            .build();

            when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
            when(itemRepository.findById(item.getId())).thenReturn(Optional.of(item));
            when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

            try {
                cartService.addCartItem(cartItemReq, userName);
            } catch (AppException e) {
                assertEquals("?????? ????????? ????????????", e.getMessage());
            }
        }

        @Test
        @DisplayName("??????????????? ?????? ??????")
        void notFoundCart() {
            UserEntity user = mock(UserEntity.class);

            when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
            when(itemRepository.findById(any(Long.class))).thenReturn(Optional.of(item));
            when(cartRepository.findByUser(user)).thenReturn(Optional.empty());

            cartService.addCartItem(cartItemReq, userName);

            verify(cartRepository, atLeastOnce()).save(any(CartEntity.class));  // ??????????????? ??????????????? ??????
        }
    }

    @Nested
    @DisplayName("???????????? ?????? ?????????")
    class findCartItemListTest {
        /*@Test
        @DisplayName("??????????????? ?????? ?????? Content??? ???????????????")
        void findCartItemList() {
            UserEntity user = mock(UserEntity.class);
            CartEntity cart = CartEntityFixture.get(user);
            PageRequest pageRequest = PageRequest.of(0, 10);

            when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
            when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));

            when(cartItemRepository.findByCart(pageRequest, cart)).thenReturn(Page.empty());

            Page<CartItemResponse> result = cartService.findCartItemList(pageRequest, userName);

            assertThat(result.getContent()).isEmpty();
        }*/
    }

    @Nested
    @DisplayName("???????????? ?????? ?????????")
    class UpdateCartItemTest {

        @Test
        @DisplayName("???????????? ?????? ?????? ?????? - ?????? ?????? < ????????? ???????????? ????????? ??????")
        void notEnoughStock() {
            UserEntity user = mock(UserEntity.class);
            CartEntity cart = mock(CartEntity.class);

            when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
            when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
            when(cartItemRepository.findByCartAndId(cart, cartItemReq.getCartItemId())).thenReturn(Optional.of(cartItem));

            assertTrue(cartItem.getItem().getItemStock() < cartItem.getCartItemCnt());
            try {
                cartService.updateCartItem(cartItemReq, userName);
            } catch (AppException e) {
                assertEquals("?????? ????????? ????????????.", e.getMessage());
            }
        }

        @Test
        @DisplayName("???????????? ?????? ?????? ??????")
        void updateItemCnt() {
            UserEntity user = mock(UserEntity.class);
            CartEntity cart = mock(CartEntity.class);

            when(userRepository.findByUserName(userName)).thenReturn(Optional.of(user));
            when(cartRepository.findByUser(user)).thenReturn(Optional.of(cart));
            when(cartItemRepository.findByCartAndId(cart, cartItemReq.getCartItemId())).thenReturn(Optional.of(cartItem));

            Integer cnt = cartService.updateCartItem(cartItemReq, userName);

            assertEquals(cartItemReq.getCartItemCnt(), cnt);
        }
    }

    @Nested
    @DisplayName("???????????? ?????? ?????????")
    class DeleteCartItemTest {

        @Test
        @DisplayName("???????????? ?????? ??????")
        void deleteCartItem() {
            Long cartItemId = TestInfoFixture.get().getItemId();

            when(userRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
            when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.of(mock(CartItemEntity.class)));

            cartService.deleteCartItem(cartItemId, userName);
            verify(cartItemRepository, atLeastOnce()).deleteById(any(Long.class));
        }

        @Test
        @DisplayName("???????????? ?????? ?????? - ???????????? ???????????? ?????? ??? ?????? ??????")
        void notFoundCartItem() {
            Long cartItemId = TestInfoFixture.get().getItemId();

            when(userRepository.findByUserName(userName)).thenReturn(Optional.of(mock(UserEntity.class)));
            when(cartItemRepository.findById(cartItemId)).thenReturn(Optional.empty());

            try {
                cartService.deleteCartItem(cartItemId, userName);
            }catch (AppException e) {
                assertEquals("??????????????? ?????? ????????? ?????? ??? ????????????.", e.getMessage());
            }
        }
    }

}