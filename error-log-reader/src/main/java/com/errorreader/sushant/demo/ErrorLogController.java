package com.errorreader.sushant.demo;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
public class ErrorLogController {

	@Autowired
	private ErrorLogRepository errorLogRepository;
	
	@Autowired
	private EmailService emailService;

	@Autowired
	private ErrorLogService errorLogService;

	@GetMapping("/")
	public String getErrorLogs(Model model, Pageable pageable) {
		try {
			final Page<ErrorLog> page = errorLogRepository.findTop20ByOrderByIdDesc(pageable);
			Integer lowerLimit = page.getNumber() * 20;
			Integer upperLimit = lowerLimit + page.getSize();
			List<SourceDTO> sourceDTOs = new ArrayList<>();
			for (Object[] sourceObject : errorLogRepository.getSourceCount(lowerLimit, upperLimit)) {
			SourceDTO source = new SourceDTO();
			source.setCount(Integer.valueOf(sourceObject[1].toString()));
			source.setSource(sourceObject[0].toString());
			sourceDTOs.add(source);
			}
			model.addAttribute("sourceCounts", sourceDTOs);
			model.addAttribute("errorLogList", page.getContent());
	        emailService.sendSimpleMail("Mr.X", "sushantp@imail.iz", Locale.US);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "hello";

	}

	@GetMapping("/get/error/logs")
	public String getAllLogs(Model model, @RequestParam Map<String, String> allRequestParam)
			throws JsonParseException, JsonMappingException, IOException, InterruptedException, ParseException {
		ErrorLogDTO errorLogSearchDTO = new ErrorLogDTO();
		errorLogSearchDTO.setCuId(allRequestParam.get("cuId"));
		errorLogSearchDTO.setUserId(allRequestParam.get("userId"));
		errorLogSearchDTO.setErrorText(allRequestParam.get("errorText"));
		errorLogSearchDTO.setSource(allRequestParam.get("source"));
		errorLogSearchDTO.setSeverity(allRequestParam.get("severity"));
		errorLogSearchDTO.setCreatedTS(allRequestParam.get("createdTS"));
		Integer _page = NumberUtils.toInt(allRequestParam.get("page"));
		ErrorLogSpecification specification = errorLogService.getErrorLogSpecification(errorLogSearchDTO);
		Map<String, Object> map = errorLogService.getAllErrorLogs(specification, _page);
		model.addAttribute("errorLogList", map.get("list"));
		Integer lowerLimit = _page * 20;
		Integer upperLimit = lowerLimit + 20;
		List<SourceDTO> sourceDTOs = new ArrayList<>();
		for (Object[] sourceObject : errorLogRepository.getSourceCount(lowerLimit, upperLimit)) {
		SourceDTO source = new SourceDTO();
		source.setCount(Integer.valueOf(sourceObject[1].toString()));
		source.setSource(sourceObject[0].toString());
		sourceDTOs.add(source);
		}
		model.addAttribute("sourceCounts", sourceDTOs);
		model.addAttribute("count", map.get("count"));
		return "hello";
	}
	
	@Scheduled(fixedRate = 1000)
	public void scheduleFixedRateTask() {
	    System.out.println(
	      "Fixed rate task - " + System.currentTimeMillis() / 1000);
	}
}