package com.nazdaq.paf.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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

import com.nazdaq.paf.model.Company;
import com.nazdaq.paf.model.Employee;
import com.nazdaq.paf.model.PAFManage;
import com.nazdaq.paf.model.PAFMst;
import com.nazdaq.paf.model.PAFYear;
import com.nazdaq.paf.model.User;
import com.nazdaq.paf.service.CommonService;
import com.nazdaq.paf.util.Constants;

@Controller
@PropertySource("classpath:common.properties")
public class PAFMstController implements Constants {
	
	@Autowired
	private CommonService commonService;

	@Autowired
	private JavaMailSender mailSender;

	@Value("${cc.email.addresss}")
	String ccEmailAddresss;

	@Value("${common.email.address}")
	String commonEmailAddress;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/pafMstList", method = RequestMethod.GET)
	public ModelAndView pafMstList(HttpSession session, Principal principal, HttpServletRequest request,
			@ModelAttribute("command") Employee employee, BindingResult result, ModelMap model) {
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		List<PAFMst> pafMstList = null;
		Integer maxPafId = (Integer) commonService.getMaxValueByObjectAndColumn("PAFYear", "id");
		if(request.isUserInRole("ROLE_ADMIN") || request.isUserInRole("ROLE_SUPER_ADMIN")){
			Company company = (Company) session.getAttribute("company");		
			pafMstList = (List<PAFMst>)(Object) commonService.getObjectListByTwoColumn("PAFMst", "pafYear.id", maxPafId.toString(),
					"employee.company.id", company.getId().toString());
			
			model.put("pafMstList", pafMstList);
			return new ModelAndView("pafMstList", model);
		} else {
			String name = principal.getName();
			User user= (User) commonService.getAnObjectByAnyUniqueColumn("User", "userName", name);
			Employee emp = (Employee) commonService.getAnObjectByAnyUniqueColumn("Employee", "punchId", user.getEmpId().toString());
			
			pafMstList = (List<PAFMst>)(Object) commonService.getObjectListByTwoColumn("PAFMst", "pafYear.id", maxPafId.toString(),
					"employee.supervisorId", emp.getPunchId().toString());
			
			model.put("pafMstList", pafMstList);
			return new ModelAndView("superPafMstList", model);
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/myPafMstList", method = RequestMethod.GET)
	public ModelAndView myPafMstList(HttpSession session, Principal principal, 
			@ModelAttribute("command") Employee employee, BindingResult result, ModelMap model) {
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		String name = principal.getName();
		User user= (User) commonService.getAnObjectByAnyUniqueColumn("User", "userName", name);
		
		Integer maxPafId = (Integer) commonService.getMaxValueByObjectAndColumn("PAFYear", "id");
		
		Employee emp = (Employee) commonService.getAnObjectByAnyUniqueColumn("Employee", "punchId", user.getEmpId().toString());
		
		List<PAFMst> pafMstList = (List<PAFMst>)(Object) commonService.getObjectListByTwoColumn("PAFMst", "pafYear.id", maxPafId.toString(),
				"employee.id", emp.getId().toString());
		
		model.put("pafMstList", pafMstList);
		return new ModelAndView("myPafMstList", model);
	}
	
	@RequestMapping(value = "/pafEligibleList", method = RequestMethod.GET)
	public ModelAndView pafEligibleList(HttpSession session, Principal principal, 
			@ModelAttribute("command") Employee employee, BindingResult result, ModelMap model) {
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		return new ModelAndView("pafEligibleList", model);
	}
	
	//sentPafToEligibleEmployee	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/sentPafToEligibleEmployee", method = RequestMethod.POST)
	public ModelAndView sentPafToEligibleEmployee(HttpSession session, Principal principal, 
			@ModelAttribute("command") Employee employee, BindingResult result, ModelMap model) {
		if(principal == null) {
			return new ModelAndView("redirect:/login");
		}
		List<String> ids = Arrays.asList(employee.getEmployeeIds().split("\\s*,\\s*"));
		System.out.println(ids);
		
		List<Employee> activeAllEmployee = (List<Employee>)(Object) commonService.getObjectListByAnyColumn("Employee", "status", "1");	
		Integer maxPafId = (Integer) commonService.getMaxValueByObjectAndColumn("PAFYear", "id");
		PAFYear pafYear = (PAFYear) commonService.getAnObjectByAnyUniqueColumn("PAFYear", "id", maxPafId.toString());
		PAFMst pafMst = null;
		for (String id : ids) {
			for (Employee emp : activeAllEmployee) {				
				if(id.equals(emp.getId().toString())) {
					pafMst = new PAFMst();
					pafMst.setPafType(OPEN);
					pafMst.setPafYear(pafYear);
					pafMst.setEmployee(emp);
					pafMst.setStatus(P_1_OPEN);
					pafMst.setCreatedBy(principal.getName());
					pafMst.setCreatedDate(new Date());
					commonService.saveOrUpdateModelObjectToDB(pafMst);
					break;
				}
			}
		}
		
		
		return new ModelAndView("redirect:/pafEligibleList", model);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getPafEligibleList",  method = RequestMethod.POST)	
	public @ResponseBody String getPafEligibleList(@RequestBody String jsonString, HttpSession session, Principal principal) throws JsonGenerationException, JsonMappingException, Exception {
		
		//Gson gson = new GsonBuilder().create();
		Boolean isJson = commonService.isJSONValid(jsonString);
		String toJson = "";
		if (isJson) {
			ObjectWriter ow = new ObjectMapper().writer()
					.withDefaultPrettyPrinter();
			//Employee employee = gson.fromJson(jsonString, Employee.class);
			
			// start
			Company company = (Company) session.getAttribute("company");
			Integer maxPafId = (Integer) commonService.getMaxValueByObjectAndColumn("PAFYear", "id");
			List<Employee> activeAllEmployee = (List<Employee>)(Object) commonService.getObjectListByAnyColumn("Employee", "status", "1");
			List<Employee> pafEligibleList = new ArrayList<Employee>();
			if(maxPafId != null) {
				//PAFYear pafYear = (PAFYear) commonService.getAnObjectByAnyUniqueColumn("PAFYear", "id", maxPafId.toString());				
				List<String> jobGradeList = new ArrayList<String>();
				jobGradeList.add("C-2");
				jobGradeList.add("C-3");
				jobGradeList.add("C-4");
				jobGradeList.add("C-5");
				jobGradeList.add("C-6");
				jobGradeList.add("C-7");
				jobGradeList.add("C-8");
				jobGradeList.add("C-9");
				jobGradeList.add("C-10");
				List<Employee> activeEmployeeList = (List<Employee>)(Object)
						commonService.getObjectListByAnyColumnValueListAndOneColumn("Employee", "jobGrade", jobGradeList, "status", "1");
				List<Employee> activeEmployeeListThisCompany = new ArrayList<Employee>();
				for (Employee emp : activeEmployeeList) {
					if(emp.getCompany() != null) {
						if(emp.getCompany().getId().toString().equals(company.getId().toString())) {
							activeEmployeeListThisCompany.add(emp);
						}
					} 			
				}
				
				List<PAFMst> pafMstList = (List<PAFMst>)(Object) commonService.getObjectListByAnyColumn("PAFMst", "pafYear.id", maxPafId.toString());
				if(pafMstList != null && pafMstList.size() > 0) {
					boolean flag = false;
					for (Employee emp : activeEmployeeListThisCompany) {
						for (PAFMst pafMst : pafMstList) {
							if(pafMst.getEmployee().getId().toString().equals(emp.getId().toString())) {
								flag = true;
								break;
							}
						}
						if(!flag) {
							pafEligibleList.add(emp);
						}
					}
				} else {
					pafEligibleList = activeEmployeeListThisCompany;
				}
				
				//supervisior name				
				for (Employee paf : pafEligibleList) {
					for (Employee emp : activeAllEmployee) {
						if(paf.getSupervisorId().toString().equals(emp.getPunchId().toString())){
							paf.setSupervisorName(emp.getName());
							break;
						}
					}
				}
			}
			
			
			
			// end 
			
			pafEligibleList.sort(Comparator.comparing(Employee::getId));
			
			toJson = ow.writeValueAsString(pafEligibleList);
		} else {
			Thread.sleep(125 * 1000);
		}
		return toJson;
		
	}
	
	
	@RequestMapping(value = {"/updatePafMst"}, method = RequestMethod.POST)
	private @ResponseBody String updatePafMst(@RequestBody String jesonString,HttpServletRequest request, Principal principal) 
			throws JsonGenerationException, JsonMappingException, Exception {
		String toJson = "";
		String result = "fail";	

		PAFMst pafMstDb = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", request.getParameter("id"));

		//personal info
		pafMstDb.setKeyResponsibilities(request.getParameter("keyResponsibilities").toString());
		pafMstDb.setUnit(request.getParameter("unit"));
		pafMstDb.setEmpEvaluation(request.getParameter("empEvaluation").trim());
		pafMstDb.setManEvaluation(request.getParameter("manEvaluation").trim());
		
		//overall performance
		pafMstDb.setEmpComments(request.getParameter("empComments").trim());
		pafMstDb.setManComments(request.getParameter("manComments").trim());
		
		if(request.getParameter("manRatings") != null)
		pafMstDb.setManRatings(Integer.parseInt(request.getParameter("manRatings").trim()));
		
		if(request.getParameter("hrRatings") != null)
		pafMstDb.setHrRatings(Integer.parseInt(request.getParameter("hrRatings").trim()));
		
		//others
		pafMstDb.setAcknowComments(request.getParameter("acknowComments").trim());
		pafMstDb.setAcknowDate(request.getParameter("acknowDate").trim());
		
		pafMstDb.setModifiedBy(principal.getName());
		pafMstDb.setModifiedDate(new Date());
		
		commonService.saveOrUpdateModelObjectToDB(pafMstDb);
		
		
	    result="Success";
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		toJson = ow.writeValueAsString(result);
		return toJson;
	}
	
	
	@RequestMapping(value = {"/pafMstModify"}, method = RequestMethod.POST)
	private @ResponseBody String pafMstModify(@RequestBody String jesonString,HttpServletRequest request, Principal principal) 
			throws JsonGenerationException, JsonMappingException, Exception {
		String toJson = "";
		String result = "fail";	

		PAFMst pafMstDb = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", request.getParameter("id"));

		//personal info
		pafMstDb.setPafType(request.getParameter("pafType"));
		pafMstDb.setStatus(request.getParameter("status"));
		
		pafMstDb.setModifiedBy(principal.getName());
		pafMstDb.setModifiedDate(new Date());
		
		commonService.saveOrUpdateModelObjectToDB(pafMstDb);
		
		
	    result="Success";
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		toJson = ow.writeValueAsString(result);
		return toJson;
	}
	
	
	@RequestMapping(value = "/p1SubmitToManager/{id}", method = RequestMethod.GET)
	public ModelAndView p1SubmitToManager(@PathVariable("id") String id, ModelMap model, Principal principal) {
		
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		PAFMst pafMstDb = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", id);
		pafMstDb.setStatus(P_1_SUBMITTED);
		pafMstDb.setModifiedBy(principal.getName());
		pafMstDb.setModifiedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(pafMstDb);
		return new ModelAndView("redirect:/pafMstList", model);
	}
	
	@RequestMapping(value = "/p1SendBackToEmployee/{id}", method = RequestMethod.GET)
	public ModelAndView p1SendBackToEmployee(@PathVariable("id") String id, ModelMap model, Principal principal) {
		
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		PAFMst pafMstDb = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", id);
		pafMstDb.setStatus(P_1_OPEN);
		pafMstDb.setModifiedBy(principal.getName());
		pafMstDb.setModifiedDate(new Date());
		// send back reason
		//pafMstDb.set
		commonService.saveOrUpdateModelObjectToDB(pafMstDb);
		return new ModelAndView("redirect:/pafMstList", model);
	}
	
	@RequestMapping(value = "/p1Approve/{id}", method = RequestMethod.GET)
	public ModelAndView p1Approve(@PathVariable("id") String id, ModelMap model, Principal principal) {
		
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		PAFMst pafMstDb = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", id);
		pafMstDb.setStatus(P_1_MANAGER_APPROVED);
		pafMstDb.setPafType(CLOSED);
		pafMstDb.setModifiedBy(principal.getName());
		pafMstDb.setModifiedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(pafMstDb);
		return new ModelAndView("redirect:/pafMstList", model);
	}
	
	@RequestMapping(value = "/p2SubmitToManager/{id}", method = RequestMethod.GET)
	public ModelAndView p2SubmitToManager(@PathVariable("id") String id, ModelMap model, Principal principal) {
		
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		PAFMst pafMstDb = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", id);
		pafMstDb.setStatus(P_2_SUBMITTED);
		pafMstDb.setModifiedBy(principal.getName());
		pafMstDb.setModifiedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(pafMstDb);
		return new ModelAndView("redirect:/pafMstList", model);
	}
	
	@RequestMapping(value = "/p2SendBackToEmployee/{id}", method = RequestMethod.GET)
	public ModelAndView p2SendBackToEmployee(@PathVariable("id") String id, ModelMap model, Principal principal) {
		
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		PAFMst pafMstDb = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", id);
		pafMstDb.setStatus(P_2_OPEN);
		pafMstDb.setModifiedBy(principal.getName());
		pafMstDb.setModifiedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(pafMstDb);
		return new ModelAndView("redirect:/pafMstList", model);
	}
	
	@RequestMapping(value = "/p2SubmitToHrd/{id}", method = RequestMethod.GET)
	public ModelAndView p2SubmitToHrd(@PathVariable("id") String id, ModelMap model, Principal principal) {
		
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		PAFMst pafMstDb = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", id);
		pafMstDb.setStatus(P_2_MANAGER_SUBMIT_TO_HRD);
		pafMstDb.setModifiedBy(principal.getName());
		pafMstDb.setModifiedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(pafMstDb);
		return new ModelAndView("redirect:/pafMstList", model);
	}
	
	@RequestMapping(value = "/p2SendBackToManager/{id}", method = RequestMethod.GET)
	public ModelAndView p2SendBackToManager(@PathVariable("id") String id, ModelMap model, Principal principal) {
		
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		PAFMst pafMstDb = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", id);
		pafMstDb.setStatus(P_2_HR_SEND_BACK_2_MANAGER);
		pafMstDb.setModifiedBy(principal.getName());
		pafMstDb.setModifiedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(pafMstDb);
		return new ModelAndView("redirect:/pafMstList", model);
	}
	
	@RequestMapping(value = "/p2Approve/{id}", method = RequestMethod.GET)
	public ModelAndView p2Approve(@PathVariable("id") String id, ModelMap model, Principal principal) {
		
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		PAFMst pafMstDb = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", id);
		pafMstDb.setStatus(P_2_MANAGER_APPROVED);
		pafMstDb.setModifiedBy(principal.getName());
		pafMstDb.setModifiedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(pafMstDb);
		return new ModelAndView("redirect:/pafMstList", model);
	}
	
	@RequestMapping(value = "/p2Disagree/{id}", method = RequestMethod.GET)
	public ModelAndView p2Disagree(@PathVariable("id") String id, ModelMap model, Principal principal) {
		
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		PAFMst pafMstDb = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", id);
		pafMstDb.setStatus(P_2_EMP_DISAGREE);
		pafMstDb.setModifiedBy(principal.getName());
		pafMstDb.setModifiedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(pafMstDb);
		return new ModelAndView("redirect:/pafMstList", model);
	}
	
	@RequestMapping(value = "/p2Agree/{id}", method = RequestMethod.GET)
	public ModelAndView p2Agree(@PathVariable("id") String id, ModelMap model, Principal principal) {
		
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		PAFMst pafMstDb = (PAFMst) commonService.getAnObjectByAnyUniqueColumn("PAFMst", "id", id);
		pafMstDb.setStatus(P_2_EMP_AGREE);
		pafMstDb.setPafType(CLOSED);
		pafMstDb.setModifiedBy(principal.getName());
		pafMstDb.setModifiedDate(new Date());
		commonService.saveOrUpdateModelObjectToDB(pafMstDb);
		return new ModelAndView("redirect:/pafMstList", model);
	}
}
