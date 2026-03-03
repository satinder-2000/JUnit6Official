package com.example.project.displayNamesGenerators;

import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.IndicativeSentencesGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@IndicativeSentencesGeneration(separator = "->", generator = ReplaceUnderscores.class)
public class A_year_is_a_leap_year {
	
	@Test
	void if_it_is_divible_by_4_but_not_by_100() {
	}
	
	@ParameterizedTest(name= "Year {0} is a leap year")
	@ValueSource(ints = {2016, 2020, 2048})
	void if_it_is_one_of_the_following_years(int year) {}

}
