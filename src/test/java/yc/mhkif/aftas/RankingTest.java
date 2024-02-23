package yc.mhkif.aftas;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yc.mhkif.aftas.repositories.HuntingRepository;
import yc.mhkif.aftas.repositories.RankingRepository;


@ExtendWith(MockitoExtension.class)
class HuntingServiceImplTest {

    @Mock
    private HuntingRepository huntingRepository ;
    @Mock
    RankingRepository rankingRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addNewHuntingToMember() {
    }

    @Test
    void rankMembersInCompetition(){
    }


    @Test
    void updateRankingScore() {
    }

    @Test
    void updateRankingRank() {
    }
}

