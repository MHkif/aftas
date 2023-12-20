package yc.mhkif.aftas;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yc.mhkif.aftas.entities.Hunting;
import yc.mhkif.aftas.entities.Ranking;
import yc.mhkif.aftas.entities.implementations.CompetitionMember;
import yc.mhkif.aftas.repositories.IHuntingRepository;
import yc.mhkif.aftas.repositories.IRankingRepository;


@ExtendWith(MockitoExtension.class)
class HuntingServiceImplTest {

    @Mock
    private IHuntingRepository huntingRepository ;
    @Mock
    IRankingRepository rankingRepository;

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

