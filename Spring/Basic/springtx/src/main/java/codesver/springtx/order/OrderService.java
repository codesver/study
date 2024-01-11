package codesver.springtx.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void order(Order order) throws NotEnoughMoneyException {
        log.info("call order");
        orderRepository.save(order);

        log.info("결제 process 진입");
        if (order.getUsername().equals("예외")) {
            log.info("System exception 발생");
            throw new RuntimeException("System exception");
        } else if (order.getUsername().equals("잔고 부족")) {
            log.info("Business exception");
            order.setPayStatus("대기");
            throw new NotEnoughMoneyException("잔고가 부족합니다.");
        } else {
            log.info("Success");
            order.setPayStatus("완료");
        }
        log.info("결제 process 완료");
    }
}
