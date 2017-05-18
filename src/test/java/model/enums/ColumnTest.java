package model.enums;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ColumnTest {

    private static final int LEFT_FOUR_SQUARES = -4;
    private static final int RIGHT_THREE_SQUARES = 3;
    private static final int RIGHT_ONE_SQUARE = 1;
    private static final int LEFT_ONE_SQUARE = -1;

    @Test
    public void column_name_format_is_as_follows() {
        Column col_a = Column.A;
        assertThat(col_a.toString(), equalTo("A"));
    }

    @Test
    public void column_to_the_left_of_columnA_is_Null() {
        assertNull(Column.A.horizontalNeighbor(LEFT_ONE_SQUARE));
    }

    @Test
    public void column_to_the_right_of_columnH_is_Null() {
        assertNull(Column.H.horizontalNeighbor(RIGHT_ONE_SQUARE));
    }

    @Test
    public void column_to_the_left_of_columnB_is_ColumnA() {
        assertEquals(Column.A, Column.B.horizontalNeighbor(LEFT_ONE_SQUARE));
    }

    @Test
    public void column_to_the_right_of_columnG_is_ColumnH() {
        assertEquals(Column.H, Column.G.horizontalNeighbor(RIGHT_ONE_SQUARE));
    }

    @Test
    public void column_3_to_the_right_of_columnD_is_ColumnG() {
        assertEquals(Column.G, Column.D.horizontalNeighbor(RIGHT_THREE_SQUARES));
    }

    @Test
    public void column_4_to_the_left_of_columnF_is_ColumnB() {
        assertEquals(Column.B, Column.F.horizontalNeighbor(LEFT_FOUR_SQUARES));
    }

}
