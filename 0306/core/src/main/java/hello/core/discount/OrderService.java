package hello.core.discount;

import hello.core.member.Member;

public interface OrderService {
    Order createOrder(Long memberId, String productName, int productPrice);
}
