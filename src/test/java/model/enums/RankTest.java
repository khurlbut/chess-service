package model.enums;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import model.enums.Rank;

import org.junit.Test;

public class RankTest {

    @Test
    public void rank_name_format_is_as_follows() {
        Rank queen = Rank.Queen;
        assertThat(queen.toString(), equalTo("Queen"));
    }

}
