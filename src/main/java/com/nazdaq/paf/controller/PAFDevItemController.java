package com.nazdaq.paf.controller;

import java.security.Principal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.google.gson.GsonBuilder;
import com.nazdaq.paf.model.PAFDevItems;
import com.nazdaq.paf.model.PAFMst;
import com.nazdaq.paf.service.CommonService;
import com.nazdaq.paf.util.Constants;

@Controller
@PropertySource("classpath:common.properties")
public class PAFDevItemController implements Constants {
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${cc.email.addresss}")
	String ccEmailAddresss;

	@Value("${common.email.address}")
	String commonEmailAddress;
	
	
	@RequestMapping(value = {"/addOrUpdateDevItem"}, method = RequestMethod.POST)
	private @ResponseBody String addOrUpdateDevItem(@RequestBody String jesonString,HttpServletRequest request, Principal principal) 
			throws JsonGenerationException, JsonMappingException, Exception {
		String toJson = "";
		String result = "fail";	
		//Gson gson = new GsonBuilder().create();	
		
		if(request.getParameter("id") != null && request.getParameter("id").length() > 0) {
			PAFDevItems itemDb = (PAFDevItems) commonService.getAnObjectByAnyUniqueColumn("PAFDevItems", "id", request.getParameter("id"));
			itemDb.setModifiedBy(principal.getName());
			itemDb.setModifiedDate(new Date());
			itemDb.setStatus(request.getParameter("status").trim());
			itemDb.setName(request.getParameter("name").trim());
			commonService.saveOrUpdateModelObjectToDB(itemDb);
		} else {
			PAFMst pafMstDb = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", request.getParameter("pafMstId"));
			PAFDevItems item = new PAFDevItems();
			item.setCreatedBy(principal.getName());
			item.setCreatedDate(new Date());
			item.setStatus(request.getParameter("status").trim());
			item.setName(request.getParameter("name").trim());
			item.setPafMst(pafMstDb);
			commonService.saveOrUpdateModelObjectToDB(item);
		}
		
	    result="Success";
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		toJson = ow.writeValueAsString(result);
		return toJson;
	}
	
	// methos to Dev Item delete
	@RequestMapping(value = "/deleteDevItem/{id}", method = RequestMethod.GET)
	public ModelAndView deleteDevItem(@PathVariable("id") String id, RedirectAttributes redirectAttributes,
			Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		PAFDevItems itemDb = (PAFDevItems) commonService.getAnObjectByAnyUniqueColumn("PAFDevItems", "id", id);
		
		commonService.deleteAnObjectById("PAFDevItems", Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("success", "Developement Item has been  deleted by you.");
		return new ModelAndView("redirect:/pafForm?id="+itemDb.getPafMst().getId());
	}
	
}
