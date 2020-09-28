package com.nazdaq.paf.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nazdaq.paf.model.PAFManage;
import com.nazdaq.paf.model.PAFYear;
import com.nazdaq.paf.service.CommonService;
import com.nazdaq.paf.util.Constants;

@Controller
@PropertySource("classpath:common.properties")
public class PAFManageController implements Constants {
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${cc.email.addresss}")
	String ccEmailAddresss;

	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@RequestMapping(value = "/newPafManageForm", method = RequestMethod.GET)
	public ModelAndView addPafManage(@ModelAttribute("command") PAFManage pafManage, BindingResult result, ModelMap model) {
		List<PAFYear> pafYearList = (List<PAFYear>)(Object) commonService.getAllObjectList("PAFYear");
		model.put("pafYearList", pafYearList);
		model.put("edit", false);
		return new ModelAndView("addPafManage", model);
	}

	@RequestMapping(value = "/savePafManage", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView savePafManage(@ModelAttribute("command") PAFManage pafManage, HttpSession session, HttpServletRequest request,
			RedirectAttributes redirectAttributes, Principal principal) {
		
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		PAFYear pafYearDb = (PAFYear) commonService.getAnObjectByAnyUniqueColumn("PAFYear", "id", pafManage.getPafYearId());
		
		if(pafYearDb != null) {
			if (pafManage.getId() != null) {
				PAFManage pafManageDb = (PAFManage) commonService.getAnObjectByAnyUniqueColumn("PAFManage", "id", pafManage.getId().toString());
				PAFManage pafManageByPafYear = (PAFManage) commonService.getAnObjectByAnyUniqueColumn("PAFManage", "pafYear.id", pafManage.getPafYearId());
				if(pafManageByPafYear != null) {
					if(pafManageDb.getId().toString().equals(pafManageByPafYear.getId().toString())) {
						pafManageDb.setModifiedBy(principal.getName());
						pafManageDb.setModifiedDate(new Date());
						
						pafManageDb.setPh1OpenDate(pafManage.getPh1OpenDate());
						pafManageDb.setPh1CloseDate(pafManage.getPh1CloseDate());
						pafManageDb.setPh2OpenDate(pafManage.getPh2OpenDate());				
						pafManageDb.setPh2CloseDate(pafManage.getPh2CloseDate());
						
						pafManageDb.setRemarks(pafManage.getRemarks());					
						redirectAttributes.addFlashAttribute("success", "One Performance Year has been  updated by you.");
						commonService.saveOrUpdateModelObjectToDB(pafManageDb);
						return new ModelAndView("redirect:/pafManageList");
					} else {
						redirectAttributes.addFlashAttribute("success", "Performance Manage Already Exists.");
						return new ModelAndView("redirect:/pafManageList");
					}
				} else {
					redirectAttributes.addFlashAttribute("success", "Action Not Success. Please Try again with right value.");
					return new ModelAndView("redirect:/pafManageList");
				}			
					
			} else {
				PAFManage pafManageByPafYear = (PAFManage) commonService.getAnObjectByAnyUniqueColumn("PAFManage", "pafYear.id", pafManage.getPafYearId());
				if(pafManageByPafYear == null) {				
					pafManage.setPafYear(pafYearDb);
					pafManage.setCreatedBy(principal.getName());
					pafManage.setCreatedDate(new Date());
					commonService.saveOrUpdateModelObjectToDB(pafManage);
					redirectAttributes.addFlashAttribute("success", "One Performance Manage has been  added by you.");
					return new ModelAndView("redirect:/pafManageList");
				} else {
					redirectAttributes.addFlashAttribute("success", "Performance Manage Already Exists.");
					return new ModelAndView("redirect:/pafManageList");
				}			
			}
		} else {
			redirectAttributes.addFlashAttribute("success", "Action Not Success. Please Try again with right value.");
			return new ModelAndView("redirect:/pafManageList");
		}
	}

	// methos to company delete
	@RequestMapping(value = "/deletePafManage/{id}", method = RequestMethod.GET)
	public ModelAndView deletePafManage(@PathVariable("id") String id, RedirectAttributes redirectAttributes,
			Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		commonService.deleteAnObjectById("PAFManage", Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("success", "One PAF Manage has been  deleted by you.");
		return new ModelAndView("redirect:/pafManageList");
	}
	
	// methos to company show
	@RequestMapping(value = "/showPafManage/{id}", method = RequestMethod.GET)
	public ModelAndView showPafManage(@PathVariable("id") String id, ModelMap model, RedirectAttributes redirectAttributes,
			Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		model.put("pafManage", (PAFManage) commonService.getAnObjectByAnyUniqueColumn("PAFManage", "id", id));
		redirectAttributes.addFlashAttribute("success", "One Paf Manage has been  deleted by you.");
		return new ModelAndView("showPafManage", model);
	}

	@RequestMapping(value = "/editPafManage/{id}", method = RequestMethod.GET)
	public ModelAndView editPafManage(@ModelAttribute("command") PAFManage pafManage, BindingResult result,
			@PathVariable("id") String id, ModelMap model, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		List<PAFYear> pafYearList = (List<PAFYear>)(Object) commonService.getAllObjectList("PAFYear");
		model.put("pafYearList", pafYearList);
		model.put("pafManage", (PAFManage) commonService.getAnObjectByAnyUniqueColumn("PAFManage", "id", id));
		model.put("edit", true);
		return new ModelAndView("editPafManage", model);

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/checkUniquePafManage", method = RequestMethod.POST)
	private @ResponseBody void checkUniquePafManage(HttpServletRequest request, Principal principal,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, Exception {
		
		String pafYearId = request.getParameter("pafYearId").toString();
		
		String toJson = "";
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		JsonObject myObj = new JsonObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		PAFManage pafManage = (PAFManage) commonService.getAnObjectByAnyUniqueColumn("PAFManage", "pafYear.id", pafYearId);
		JsonElement pafManageObject = gson.toJsonTree(pafManage);
		myObj.add("pafManageInfo", pafManageObject);
		out.println(myObj.toString());

		out.close();

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pafManageList", method = RequestMethod.GET)
	public ModelAndView pafManageList(ModelMap model, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		List<PAFManage> pafManageList = (List<PAFManage>) (Object) commonService.getAllObjectList("PAFManage");
		model.put("pafManageList", pafManageList);
		return new ModelAndView("pafManageList", model);
	}
}
