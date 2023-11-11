package com.amigoscode.customer;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class CustomerRawMapperTest {

    @Test
    void mapRow() throws SQLException {
        //Given
        CustomerRawMapper customerRawMapper = new CustomerRawMapper();

        ResultSet resultSet=Mockito.mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getInt("age")).thenReturn(19);
        when(resultSet.getString("name")).thenReturn("Jamila");
        when(resultSet.getString("email")).thenReturn("jamila@gmail.com");

        //When
        Customer actual = (Customer) customerRawMapper.mapRow(resultSet,1);
        //Then
        Customer expected = new Customer(
                1,"Jamila","jamila@gmail.com",19
        );
        assertThat(actual).isEqualTo(expected);
    }

}