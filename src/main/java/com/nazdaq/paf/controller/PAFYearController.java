package com.nazdaq.paf.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
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
import com.nazdaq.paf.model.PAFYear;
import com.nazdaq.paf.service.CommonService;
import com.nazdaq.paf.util.Constants;

@Controller
@PropertySource("classpath:common.properties")
public class PAFYearController implements Constants {
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${cc.email.addresss}")
	String ccEmailAddresss;

	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@RequestMapping(value = "/newPafYearForm", method = RequestMethod.GET)
	public ModelAndView addPafYear(@ModelAttribute("command") PAFYear pafYear, BindingResult result, ModelMap model) {
		
		model.put("edit", false);
		return new ModelAndView("addPafYear", model);
	}

	@RequestMapping(value = "/savePafYear", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView savePafYear(@ModelAttribute("command") PAFYear pafYear, HttpSession session, HttpServletRequest request,
			RedirectAttributes redirectAttributes, Principal principal) {
		
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		String fromDate = pafYear.getFromDate();
		String toDate = pafYear.getToDate();
		String fromYear = fromDate.substring(fromDate.length() -4, fromDate.length());
		String toYear = toDate.substring(toDate.length() -4, toDate.length());
		String pafYearName = (fromYear+"-"+toYear);
		
		if((Integer.parseInt(toYear) - Integer.parseInt(fromYear)) == 1) {
			if (pafYear.getId() != null) {
				PAFYear pafYearDb = (PAFYear) commonService.getAnObjectByAnyUniqueColumn("PAFYear", "id", pafYear.getId().toString());
				PAFYear pafYearByName = (PAFYear) commonService.getAnObjectByAnyUniqueColumn("PAFYear", "name", pafYearName);
				if(pafYearByName != null) {
					if(pafYearDb.getId().toString().equals(pafYearByName.getId().toString())) {
						pafYearDb.setModifiedBy(principal.getName());
						pafYearDb.setModifiedDate(new Date());
						pafYearDb.setFromDate(pafYear.getFromDate());
						pafYearDb.setToDate(pafYear.getToDate());					
						pafYearDb.setName(pafYearName);
						pafYearDb.setRemarks(pafYear.getRemarks());					
						redirectAttributes.addFlashAttribute("success", "One Performance Year has been  updated by you.");
						commonService.saveOrUpdateModelObjectToDB(pafYearDb);
						return new ModelAndView("redirect:/pafYearList");
					} else {
						redirectAttributes.addFlashAttribute("success", "Performance Year Already Exists.");
						return new ModelAndView("redirect:/pafYearList");
					}
				} else {
					redirectAttributes.addFlashAttribute("success", "Action Not Success. Please Try again with right value.");
					return new ModelAndView("redirect:/pafYearList");
				}			
				
			} else {
				PAFYear pafYearByName = (PAFYear) commonService.getAnObjectByAnyUniqueColumn("PAFYear", "name", pafYearName);
				if(pafYearByName == null) {				
					pafYear.setName(pafYearName);
					pafYear.setCreatedBy(principal.getName());
					pafYear.setCreatedDate(new Date());
					commonService.saveOrUpdateModelObjectToDB(pafYear);
					redirectAttributes.addFlashAttribute("success", "One Performance Year has been  added by you.");
					return new ModelAndView("redirect:/pafYearList");
				} else {
					redirectAttributes.addFlashAttribute("success", "Performance Year Already Exists.");
					return new ModelAndView("redirect:/pafYearList");
				}			
			}
		} else {
			redirectAttributes.addFlashAttribute("success", "Action Not Success. Please Try again with right value.");
			return new ModelAndView("redirect:/pafYearList");
		}
		
	}

	// methos to company delete
	@RequestMapping(value = "/deletePafYear/{id}", method = RequestMethod.GET)
	public ModelAndView deletePafYear(@PathVariable("id") String id, RedirectAttributes redirectAttributes,
			Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		commonService.deleteAnObjectById("PAFYear", Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("success", "One PAF Year has been  deleted by you.");
		return new ModelAndView("redirect:/pafYearList");
	}
	
	// methos to company show
	@RequestMapping(value = "/showPafYear/{id}", method = RequestMethod.GET)
	public ModelAndView showPafYear(@PathVariable("id") String id, ModelMap model, RedirectAttributes redirectAttributes,
			Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		model.put("pafYear", (PAFYear) commonService.getAnObjectByAnyUniqueColumn("PAFYear", "id", id));
		redirectAttributes.addFlashAttribute("success", "One Paf Year has been  deleted by you.");
		return new ModelAndView("showPafYear", model);
	}

	@RequestMapping(value = "/editPafYear/{id}", method = RequestMethod.GET)
	public ModelAndView editPafYear(@ModelAttribute("command") PAFYear pafYear, BindingResult result,
			@PathVariable("id") String id, ModelMap model, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		model.put("pafYear", (PAFYear) commonService.getAnObjectByAnyUniqueColumn("PAFYear", "id", id));
		model.put("edit", true);
		return new ModelAndView("editPafYear", model);

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/checkUniquePafYear", method = RequestMethod.POST)
	private @ResponseBody void checkUniquePafYear(HttpServletRequest request, Principal principal,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, Exception {
		
		String fromDate = request.getParameter("fromDate").toString();
		String toDate = request.getParameter("toDate").toString();
		String fromYear = fromDate.substring(fromDate.length() -4, fromDate.length());
		String toYear = toDate.substring(toDate.length() -4, toDate.length());
		String pafYearName = (fromYear+"-"+toYear);
		
		String toJson = "";
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		JsonObject myObj = new JsonObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		PAFYear pafYear = (PAFYear) commonService.getAnObjectByAnyUniqueColumn("PAFYear", "name", pafYearName);
		JsonElement PAFYearObject = gson.toJsonTree(pafYear);
		myObj.add("pafYearInfo", PAFYearObject);
		out.println(myObj.toString());

		out.close();

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pafYearList", method = RequestMethod.GET)
	public ModelAndView pafYearList(ModelMap model, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		List<PAFYear> pafYearList = (List<PAFYear>) (Object) commonService.getAllObjectList("PAFYear");
		model.put("pafYearList", pafYearList);
		return new ModelAndView("pafYearList", model);
	}

}
