package kr.co.teamA.Haru.security.repo;

import kr.co.teamA.Haru.security.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
