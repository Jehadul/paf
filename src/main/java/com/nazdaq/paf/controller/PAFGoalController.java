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
import com.nazdaq.paf.model.PAFGoals;
import com.nazdaq.paf.model.PAFMst;
import com.nazdaq.paf.service.CommonService;
import com.nazdaq.paf.util.Constants;

@Controller
@PropertySource("classpath:common.properties")
public class PAFGoalController implements Constants {
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${cc.email.addresss}")
	String ccEmailAddresss;

	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@RequestMapping(value = {"/addOrUpdateGoal"}, method = RequestMethod.POST)
	private @ResponseBody String addOrUpdateGoal(@RequestBody String jesonString,HttpServletRequest request, Principal principal) 
			throws JsonGenerationException, JsonMappingException, Exception {
		String toJson = "";
		String result = "fail";	
				

		if(request.getParameter("id") != null && request.getParameter("id").length() > 0) {
			PAFGoals goalDb = (PAFGoals) commonService.getAnObjectByAnyUniqueColumn("PAFGoals", "id", request.getParameter("id"));
			goalDb.setModifiedBy(principal.getName());
			goalDb.setModifiedDate(new Date());
			
			goalDb.setStatus(request.getParameter("status").trim());		
			goalDb.setGoal(request.getParameter("goal").trim());
			goalDb.setDueDate(request.getParameter("dueDate").trim());
			goalDb.setCompleteDate(request.getParameter("completeDate").trim());
			goalDb.setEmpEval(request.getParameter("empEval").trim());
			goalDb.setManEval(request.getParameter("manEval").trim());
			commonService.saveOrUpdateModelObjectToDB(goalDb);
		} else {
			PAFMst pafMstDb = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", request.getParameter("pafMstId"));
			PAFGoals goal = new PAFGoals();
			goal.setCreatedBy(principal.getName());
			goal.setCreatedDate(new Date());
			
			goal.setStatus(request.getParameter("status").trim());		
			goal.setGoal(request.getParameter("goal").trim());
			goal.setDueDate(request.getParameter("dueDate").trim());
			goal.setCompleteDate(request.getParameter("completeDate").trim());
			goal.setEmpEval(request.getParameter("empEval").trim());
			goal.setManEval(request.getParameter("manEval").trim());
			
			goal.setPafMst(pafMstDb);
			commonService.saveOrUpdateModelObjectToDB(goal);
		}
		
	    result="Success";
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		toJson = ow.writeValueAsString(result);
		return toJson;
	}
	
	// methos to Dev Item delete
	@RequestMapping(value = "/deleteGoal/{id}", method = RequestMethod.GET)
	public ModelAndView deleteDevItem(@PathVariable("id") String id, RedirectAttributes redirectAttributes,
			Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		PAFGoals goals = (PAFGoals) commonService.getAnObjectByAnyUniqueColumn("PAFGoals", "id", id);
		
		commonService.deleteAnObjectById("PAFGoals", Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("success", "Developement Item has been  deleted by you.");
		return new ModelAndView("redirect:/pafForm?id="+goals.getPafMst().getId());
	}

}
