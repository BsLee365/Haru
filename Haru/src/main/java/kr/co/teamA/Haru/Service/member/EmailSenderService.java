package kr.co.teamA.Haru.Service.member;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kr.co.teamA.Haru.CertificationNumberDAO;
import kr.co.teamA.Haru.Repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CertificationNumberDAO certificationNumberDAO;

    private String authCode;


    public int duplicateEmail(String email) {
        int checkEmail = memberRepository.findUserEmailByEmail(email);
        return checkEmail > 0 ? 1 : 0;
    }

    public void createAuthCode () {
        int length = 6;
        StringBuilder authCode = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int type = random.nextInt(3);

            switch(type) {
                case 0:
                    authCode.append(random.nextInt(10));
                    break;
                case 1:
                    authCode.append((char) (random.nextInt(26) + 65));
                    break;
                case 2:
                    authCode.append((char) (random.nextInt(26) + 97));
                    break;
            }
        }
        this.authCode = authCode.toString();
    }
    // 회원가입 : 이메일 인증번호 발송
    public void sendEmail(String toEmail) {
        createAuthCode();
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("rhdudwnd82@naver.com");
            helper.setTo(toEmail);
            helper.setSubject("하루의 여울 회원가입 인증번호 발송");

            String body = "<html>" +
                    "<body>" +
                    "<h1>하루의 여울 회원가입을 위한 인증번호</h1>" +
                    "<p>회원가입을 완료하기 위해 아래의 인증코드를 입력해주세요.</p>" +
                    "<p>인증코드: <strong>" + authCode + "</strong></p>" +
                    "</body>" +
                    "</html>";

            helper.setText(body, true);
            mailSender.send(message);
            certificationNumberDAO.saveCertificationNumber(toEmail, authCode);
            
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
     }
     // 아이디 찾기 : 이메일 인증번호 발송
    public void sendFindByIdEmail(String toEmail) {
        createAuthCode();
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("rhdudwnd82@naver.com");
            helper.setTo(toEmail);
            helper.setSubject("하루의 여울 아이디 찾기 인증번호 발송");

            String body = "<html>" +
                    "<body>" +
                    "<h1>하루의 여울 아이디 찾기를 위한 인증번호</h1>" +
                    "<p>아이디 찾기를 완료하기 위해 아래의 인증코드를 입력해주세요.</p>" +
                    "<p>인증코드: <strong>" + authCode + "</strong></p>" +
                    "</body>" +
                    "</html>";

            helper.setText(body, true);
            mailSender.send(message);
            certificationNumberDAO.saveCertificationNumber(toEmail, authCode);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // 비밀번호 찾기 : 이메일 인증번호 발송
    public void sendFindByPwdEmail(String toEmail) {
        createAuthCode();
        MimeMessage message = mailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("rhdudwnd82@naver.com");
            helper.setTo(toEmail);
            helper.setSubject("하루의 여울 아이디 찾기 인증번호 발송");

            String body = "<html>" +
                    "<body>" +
                    "<h1>하루의 여울 비밀번호 찾기를 위한 인증번호</h1>" +
                    "<p>비밀번호 찾기를 완료하기 위해 아래의 인증코드를 입력해주세요.</p>" +
                    "<p>인증코드: <strong>" + authCode + "</strong></p>" +
                    "</body>" +
                    "</html>";

            helper.setText(body, true);
            mailSender.send(message);
            certificationNumberDAO.saveCertificationNumber(toEmail, authCode);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }




    public boolean isVerify(String email, String authCode) {
        System.out.println(certificationNumberDAO.hasKey(email));
        System.out.println(authCode);
        System.out.println(certificationNumberDAO.getCertificationNumber(email));
        if (certificationNumberDAO.hasKey(email) && certificationNumberDAO
                .getCertificationNumber(email).equals(authCode)) {
            certificationNumberDAO.deleteCertificationNumber(email);
            return true;
        }
        else {
            return false;
        }
     }
}
