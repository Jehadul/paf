package com.nazdaq.paf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.itextpdf.text.Utilities;
import com.nazdaq.paf.model.Company;
import com.nazdaq.paf.model.Employee;
import com.nazdaq.paf.model.PAFDevItems;
import com.nazdaq.paf.model.PAFGoals;
import com.nazdaq.paf.model.PAFMst;
import com.nazdaq.paf.model.PAFYear;
import com.nazdaq.paf.service.CommonService;
import com.nazdaq.paf.util.Constants;
import com.nazdaq.paf.util.UtilitiesFunction;

@Controller
@PropertySource("classpath:common.properties")
public class FormController implements Constants {
	// private boolean isMultipart;
	// private String filePath;
	// private int maxFileSize = 50 * 1024;
	// private int maxMemSize = 4 * 1024;
	// private File file;

	@Autowired
	private CommonService commonService;

	@SuppressWarnings("unused")
	@Autowired
	private JavaMailSender mailSender;

	@Value("${cc.email.addresss}")
	String ccEmailAddresss;

	@Value("${common.email.address}")
	String commonEmailAddress;

	@RequestMapping(value = "/pafForm", method = RequestMethod.GET)
	public ModelAndView addCompany(ModelMap model, HttpServletRequest request, UtilitiesFunction utilitiesFunction,Principal principal) {
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		PAFMst pafMst = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", request.getParameter("id"));
		
		PAFYear pafYear = pafMst.getPafYear();
		
		
		List<PAFDevItems> pafDevItems = (List<PAFDevItems>)(Object)
				commonService.getObjectListByAnyColumn("PAFDevItems", "pafMst.id", request.getParameter("id"));
		
		List<PAFGoals> goals = (List<PAFGoals>)(Object)
				commonService.getObjectListByAnyColumn("PAFGoals", "pafMst.id", request.getParameter("id"));
		
		String fromDate = pafYear.getFromDate();
		String toDate = pafYear.getToDate();
		String fromYear = fromDate.substring(fromDate.length() -4, fromDate.length());
		String toYear = toDate.substring(toDate.length() -4, toDate.length());
		String duration = "July "+ fromYear + " - June " + toYear;
		model.put("pafMst", pafMst);
		model.put("pafDevItems", pafDevItems);
		model.put("goals", goals);
		model.put("pafYear", pafYear.getName());
		model.put("duration", duration);
		
		pafMst.getEmployee()
				.setDateJoined(utilitiesFunction.getCustomDateFromDateFormate(pafMst.getEmployee().getDateJoined()));
		return new ModelAndView("pafForm", model);
	}

}
