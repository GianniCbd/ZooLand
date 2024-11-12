package natEst.nat.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/score")
public class ScoreCTRL {

    @Autowired
    private ScoreService scoreService;


    @PostMapping("/submit-score")
    public Score submitScore(@RequestBody ScoreDTO scoreDTO) {
        return scoreService.submitScore(scoreDTO);
    }

    @GetMapping("/leaderboard")
    public List<Score> getLeaderboard() {
        return scoreService.getLeaderboard();
    }
}
