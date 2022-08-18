package codesver.springtx.propagation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final LogRepository logRepository;

    @Transactional
    public void joinV1(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("== Start call of memberRepository ==");
        memberRepository.save(member);
        log.info("== End call of memberRepository ==");

        log.info("== Start call of logRepository ==");
        logRepository.save(logMessage);
        log.info("== End call of logRepository ==");
    }

    @Transactional
    public void joinV2(String username) {
        Member member = new Member(username);
        Log logMessage = new Log(username);

        log.info("== Start call of memberRepository ==");
        memberRepository.save(member);
        log.info("== End call of memberRepository ==");

        log.info("== Start call of logRepository ==");
        try {
            logRepository.save(logMessage);
        } catch (RuntimeException e) {
            log.info("Failed to save log");
            log.info("Return to main flow");
        }
        log.info("== End call of logRepository ==");
    }
}
