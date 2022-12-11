package ua.kotsko.project.Examinator.utils;

import java.util.List;

import ua.kotsko.project.Examinator.dto.ResultDTO;

public class AverageMarkUtil {

	public static Long calculate(List<ResultDTO> results) {
		if (results == null)
			return 0L;
		Double average = results.stream()
				.mapToLong(x -> x.getMark())
				.average()
				.getAsDouble();
		return average.longValue();
	}
}
