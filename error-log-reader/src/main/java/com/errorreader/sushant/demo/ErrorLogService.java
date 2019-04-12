package com.errorreader.sushant.demo;

import java.io.File;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ErrorLogService {

	@Autowired
	private ErrorLogRepository errorLogRepository;

	public Map<String, Object> getAllErrorLogs(ErrorLogSpecification specification, Integer page) {
		Map<String, Object> map = new HashMap<>();
		@SuppressWarnings("deprecation")
		Pageable ten = new PageRequest(page, 1000, Sort.Direction.DESC, "id");
		Page<ErrorLog> fetchRecords = errorLogRepository.findAll(specification, ten);
		map.put("list", fetchRecords.getContent());
		map.put("count", this.getCount(specification));
		return map;
	}

	public Integer getCount(ErrorLogSpecification specification) {
		Integer count = 0;
		List<ErrorLog> all = errorLogRepository.findAll(specification);
		if (!all.isEmpty())
			count = all.size();
		return count;
	}

	public ErrorLogSpecification getErrorLogSpecification(ErrorLogDTO search) throws ParseException {
		ErrorLogSpecification specification = null;
		SearchCriteria errorText = null, stackTrace = null, cuId = null, userId = null, source = null, severity = null,
				startDate = null;
		List<Object> values = new ArrayList<Object>();

		if (Objects.nonNull(search)) {

			if (StringUtils.isNotBlank(search.getErrorText())) {
				values = new ArrayList<Object>();
				values.add(search.getErrorText());
				errorText = new SearchCriteria("errorText", ":", values);
				specification = new ErrorLogSpecification(errorText);
			}

			if (StringUtils.isNotBlank(search.getStackTrace())) {
				values = new ArrayList<Object>();
				values.add(search.getStackTrace());
				stackTrace = new SearchCriteria("stackTrace", ":", values);
				ErrorLogSpecification remarksSpecification = new ErrorLogSpecification(stackTrace);
				if (specification != null) {
					specification.or(remarksSpecification);
				} else {
					specification = new ErrorLogSpecification(stackTrace);
				}
			}

			if (StringUtils.isNotBlank(search.getCuId())) {
				values = new ArrayList<Object>();
				values.add(Long.parseLong(search.getCuId()));
				cuId = new SearchCriteria("cuId", ":", values);
				ErrorLogSpecification assignToSpecification = new ErrorLogSpecification(cuId);
				if (specification != null) {
					specification.or(assignToSpecification);
				} else {
					specification = new ErrorLogSpecification(cuId);
				}
			}

			if (StringUtils.isNotBlank(search.getUserId())) {
				values = new ArrayList<Object>();
				values.add(Long.parseLong(search.getUserId()));
				userId = new SearchCriteria("userId", ":", values);
				ErrorLogSpecification assignToSpecification = new ErrorLogSpecification(userId);
				if (specification != null) {
					specification.and(assignToSpecification);
				} else {
					specification = new ErrorLogSpecification(userId);
				}
			}

			if (StringUtils.isNotBlank(search.getSource())) {
				values = new ArrayList<Object>();
				values.add(search.getSource());
				source = new SearchCriteria("source", ":", values);
				ErrorLogSpecification assignToSpecification = new ErrorLogSpecification(source);
				if (specification != null) {
					specification.and(assignToSpecification);
				} else {
					specification = new ErrorLogSpecification(source);
				}
			}

			if (StringUtils.isNotBlank(search.getSeverity())) {
				values = new ArrayList<Object>();
				values.add(search.getSeverity());
				severity = new SearchCriteria("severity", ":", values);
				ErrorLogSpecification assignToSpecification = new ErrorLogSpecification(severity);
				if (specification != null) {
					specification.and(assignToSpecification);
				} else {
					specification = new ErrorLogSpecification(severity);
				}
			}

			if (StringUtils.isNotBlank(search.getCreatedTS())) {
				values = new ArrayList<Object>();
				startDate = new SearchCriteria("createdTS", ":", values);
				ErrorLogSpecification dateSpecification = new ErrorLogSpecification(startDate);
				if (specification != null) {
					specification.and(dateSpecification);
				} else {
					specification = new ErrorLogSpecification(startDate);
				}
			}
		}
		return specification;
	}
	
	public JFreeChart prepareImageFromData(List<Object[]> objects) throws Exception {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for (Object object[] : objects) {
			dataset.addValue((Number) object[1], object[0].toString(),object[0].toString());
		}

		JFreeChart chart = ChartFactory.createBarChart("Error Log Statistics", "Category", "Score", dataset,
				PlotOrientation.VERTICAL, true, true, false);

		int width = 640; /* Width of the image */
		int height = 480; /* Height of the image */
		File BarChart = new File("src/main/resources/static/images/author/BarChart.jpg");
		ChartUtilities.saveChartAsJPEG(BarChart, chart, width, height);
		return chart;
	}

}
