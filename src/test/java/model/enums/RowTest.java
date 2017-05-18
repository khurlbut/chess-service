package model.enums;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RowTest {

    @Test
    public void row_name_format_is_as_follows() {
        Row row_1 = Row.R1;
        assertThat(row_1.toString(), equalTo("1"));
    }

    @Test
    public void row_one_Down_from_row1_is_Null() {
        assertNull(Row.R1.verticalNeighbor(-1));
    }

    @Test
    public void row_one_Up_from_row8_is_Null() {
        assertNull(Row.R8.verticalNeighbor(1));
    }

    @Test
    public void row_one_Up_from_row1_is_row2() {
        assertEquals(Row.R2, Row.R1.verticalNeighbor(1));
    }

    @Test
    public void row_one_Down_from_row8_is_row7() {
        assertEquals(Row.R7, Row.R8.verticalNeighbor(-1));
    }

    @Test
    public void row_three_up_from_row3_is_row6() {
        assertEquals(Row.R6, Row.R3.verticalNeighbor(3));
    }

    @Test
    public void row_four_down_from_row_6_is_row2() {
        assertEquals(Row.R2, Row.R6.verticalNeighbor(-4));
    }

}
