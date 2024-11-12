package natEst.nat.score;

import natEst.nat.users.User;
import natEst.nat.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScoreRepository scoreRepository;

    public Score submitScore(ScoreDTO scoreDTO){
        User user = userRepository.findByName(scoreDTO.name());
        if (user == null) {
            user = new User();
            user.setName(scoreDTO.name());
            userRepository.save(user);
        }
        Score scores = new Score();
        scores.setUser(user);
        scores.setScore(scoreDTO.score());
        return scoreRepository.save(scores);
    }

    public List<Score> getLeaderboard() {
        return scoreRepository.findTop10ByOrderByScoreDesc();
    }
}
