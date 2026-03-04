package com.example.project.displayNamesGenerators;

import org.junit.jupiter.api.DisplayNameGenerator.IndicativeSentences.SentenceFragment;
import org.junit.jupiter.api.IndicativeSentencesGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@SentenceFragment("A year is a leap year")
@IndicativeSentencesGeneration
class LeapYearTests {
	
	@SentenceFragment("if it is divible by 4 but not by 100")
	@Test
	void divisibleBy4ButNotBy100() {}
	
	@SentenceFragment("if it is one of the following years")
	@ParameterizedTest(name= "{0}")
	@ValueSource(ints = {2016, 2020, 2048})
	void validLeapYear(int year) {}

}
