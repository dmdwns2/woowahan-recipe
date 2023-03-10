package com.woowahan.recipe.service;

import com.woowahan.recipe.domain.OrderStatus;
import com.woowahan.recipe.domain.dto.orderDto.OrderCreateReqDto;
import com.woowahan.recipe.domain.dto.orderDto.OrderCreateResDto;
import com.woowahan.recipe.domain.entity.*;
import com.woowahan.recipe.exception.AppException;
import com.woowahan.recipe.exception.ErrorCode;
import com.woowahan.recipe.repository.ItemRepository;
import com.woowahan.recipe.repository.OrderCustomRepository;
import com.woowahan.recipe.repository.OrderRepository;
import com.woowahan.recipe.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    OrderCustomRepository orderCustomRepository = Mockito.mock(OrderCustomRepository.class);
    ItemRepository itemRepository = Mockito.mock(ItemRepository.class);
    UserRepository userRepository = Mockito.mock(UserRepository.class);
    UserEntity user = Mockito.mock(UserEntity.class);
    ItemEntity item = Mockito.mock(ItemEntity.class);
    OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(orderRepository, orderCustomRepository, userRepository, itemRepository);
    }

    // given
    UserEntity givenUser = UserEntity.builder()
            .userName("test")
            .name("test")
            .build();

    ItemEntity givenItem = ItemEntity.builder()
            .id(1L)
            .name("양파")
            .itemPrice(1000)
            .itemStock(10)
            .build();

    DeliveryEntity delivery = new DeliveryEntity(givenUser.getAddress(), DeliveryStatus.READY);

    @Test
    void 상품주문() throws Exception {
        // given
        OrderCreateReqDto reqDto = OrderCreateReqDto.builder()
                .itemId(1L)
                .count(4)
                .build();

        OrderItemEntity givenOrderItem = OrderItemEntity.createOrderItem(givenItem, reqDto.getCount());
        OrderEntity givenOrder = OrderEntity.createOrder(givenUser, delivery, givenOrderItem, "imp_uid");
        when(userRepository.findByUserName(givenUser.getUserName())).thenReturn(Optional.of(givenUser));
        when(itemRepository.findById(givenItem.getId())).thenReturn(Optional.of(givenItem));
        when(orderRepository.findById(givenOrderItem.getId())).thenReturn(Optional.of(givenOrder));

        // when
        OrderCreateResDto order = orderService.createOrder(givenUser.getUserName(), reqDto);

        // then
        assertEquals(OrderStatus.ORDER, order.getOrderStatus()); // 주문 상태
        assertEquals(4, order.getTotalCount()); // 주문 수량
        assertEquals(4000, order.getTotalPrice()); // 주문 가격
        assertEquals(2, givenItem.getItemStock()); // 재고 수량 확인
    }

    @Test
    void 상품주문_재고수량초과() throws Exception {
        // given
        OrderCreateReqDto reqDto = OrderCreateReqDto.builder()
                .itemId(1L)
                .count(20)
                .build();
        // when
        AppException notEnoughStockException = assertThrows(AppException.class, () -> {
            OrderItemEntity.createOrderItem(givenItem, reqDto.getCount());
        });
        // then
        assertEquals(ErrorCode.NOT_ENOUGH_STOCK.getMessage(), notEnoughStockException.getMessage());
    }

}