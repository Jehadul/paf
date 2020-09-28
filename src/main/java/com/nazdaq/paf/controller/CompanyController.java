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
import com.nazdaq.paf.model.Company;
import com.nazdaq.paf.service.CommonService;
import com.nazdaq.paf.util.Constants;

@Controller
@PropertySource("classpath:common.properties")
public class CompanyController implements Constants {
	//private boolean isMultipart;
	//private String filePath;
	//private int maxFileSize = 50 * 1024;
	//private int maxMemSize = 4 * 1024;
	//private File file;

	@Autowired
	private CommonService commonService;

	@SuppressWarnings("unused")
	@Autowired
	private JavaMailSender mailSender;

	@Value("${cc.email.addresss}")
	String ccEmailAddresss;

	@Value("${common.email.address}")
	String commonEmailAddress;

	@RequestMapping(value = "/newCompanyForm", method = RequestMethod.GET)
	public ModelAndView addCompany(@ModelAttribute("command") Company company, BindingResult result, ModelMap model) {
		
		model.put("edit", false);
		return new ModelAndView("addCompany", model);
	}

	@RequestMapping(value = "/saveCompany", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView addCompany(@ModelAttribute("command") Company company, @RequestParam CommonsMultipartFile file,
			HttpSession session, @RequestParam("companyLogo") String imageValue, HttpServletRequest request,
			RedirectAttributes redirectAttributes, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		
		try {
			if (!file.isEmpty()) {
				this.processarAvatar(company, file);
				company.setCompanyLogo(company.getCompanyKeyword());
			}

		} catch (Exception e) {
			System.out.println(e);
		}
		
		if (company.getId() != null) {
			Company company2 = (Company) commonService.getAnObjectByAnyUniqueColumn("Company", "id",
					company.getId().toString());
			company2.setModifiedBy(principal.getName());
			company2.setModifiedDate(new Date());	
			company2.setOfficeAddress(company.getOfficeAddress());
			company2.setName(company.getName());
			company2.setEmail(company.getEmail());
			company2.setWebSite(company.getWebSite());
			company2.setContactNo(company.getContactNo());
			redirectAttributes.addFlashAttribute("success", "One  company has been  updated by you.");
			commonService.saveOrUpdateModelObjectToDB(company2);
			return new ModelAndView("redirect:/companyList");
			
		} else {
			company.setCreatedBy(principal.getName());
			company.setCreatedDate(new Date());
			company.setCompanyKeyword(company.getCompanyKeyword().toUpperCase());
			commonService.saveOrUpdateModelObjectToDB(company);
			redirectAttributes.addFlashAttribute("success", "One  company has been  added by you.");
			return new ModelAndView("redirect:/companyList");
		}

		
	}

	private void processarAvatar(Company company, MultipartFile photo) {
		File diretorio = new File("/synergy-paf/upload/companyLogo");
		if (!diretorio.exists()) {
			diretorio.mkdirs();
		}
		try {
			FileOutputStream arquivo = new FileOutputStream(
					diretorio.getAbsolutePath() + "/" + company.getCompanyKeyword() + ".png");
			arquivo.write(photo.getBytes());
			arquivo.close();
		} catch (IOException ex) {

		}

	}

	// methos to company delete
	@RequestMapping(value = "/deleteCompany/{id}", method = RequestMethod.GET)
	public ModelAndView deleteCompany(@PathVariable("id") String id, RedirectAttributes redirectAttributes,
			Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		commonService.deleteAnObjectById("Company", Integer.parseInt(id));
		redirectAttributes.addFlashAttribute("success", "One company has been  deleted by you.");
		return new ModelAndView("redirect:/companyList");
	}
	
	// methos to company show
	@RequestMapping(value = "/showCompany/{id}", method = RequestMethod.GET)
	public ModelAndView showCompany(@PathVariable("id") String id, ModelMap model, RedirectAttributes redirectAttributes,
			Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		model.put("company", (Company) commonService.getAnObjectByAnyUniqueColumn("Company", "id", id));
		redirectAttributes.addFlashAttribute("success", "One company has been  deleted by you.");
		return new ModelAndView("showCompany", model);
	}

	@RequestMapping(value = "/editCompany/{id}", method = RequestMethod.GET)
	public ModelAndView editCompany(@ModelAttribute("command") Company company, BindingResult result,
			@PathVariable("id") String id, ModelMap model, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		model.put("company", (Company) commonService.getAnObjectByAnyUniqueColumn("Company", "id", id));
		model.put("edit", true);
		return new ModelAndView("editCompany", model);

	}

	@RequestMapping("/companyLogo/{keyword}")
	@ResponseBody
	public byte[] photo(@PathVariable("keyword") String keyword) throws IOException {
		File arquivo = new File("/synergy-paf/upload/companyLogo/" + keyword + ".png");

		if (!arquivo.exists()) {
			arquivo = new File("/synergy-paf/upload/companyLogo/photo.png");
		}

		byte[] resultado = new byte[(int) arquivo.length()];
		FileInputStream input = new FileInputStream(arquivo);
		input.read(resultado);
		input.close();
		return resultado;
	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/checkUnicKEmail", method = RequestMethod.POST)
	private @ResponseBody void checkUnicKEmail(HttpServletRequest request, Principal principal,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, Exception {

		String toJson = "";
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		JsonObject myObj = new JsonObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Company company = (Company) commonService.getAnObjectByAnyUniqueColumn("Company", "email",
				request.getParameter("email").toString());
		JsonElement reagentobj = gson.toJsonTree(company);
		myObj.add("companyInfo", reagentobj);
		out.println(myObj.toString());

		out.close();

	}

	@SuppressWarnings("unused")
	@RequestMapping(value = "/checkUnicKcompanyKeyword", method = RequestMethod.POST)
	private @ResponseBody void checkUnicKcompanyKeyword(HttpServletRequest request, Principal principal,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, Exception {
		String toJson = "";
		PrintWriter out = response.getWriter();
		Gson gson = new Gson();
		JsonObject myObj = new JsonObject();
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		Company company = (Company) commonService.getAnObjectByAnyUniqueColumn("Company", "companyKeyword",
				request.getParameter("companyKeyword").toString());
		JsonElement reagentobj = gson.toJsonTree(company);
		myObj.add("companyInfo", reagentobj);
		out.println(myObj.toString());

		out.close();

	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/companyList", method = RequestMethod.GET)
	public ModelAndView companyList(ModelMap model, Principal principal) {
		if (principal == null) {
			return new ModelAndView("redirect:/login");
		}
		List<Company> companies = (List<Company>) (Object) commonService.getAllObjectList("Company");
		model.put("companies", companies);
		return new ModelAndView("companyList", model);
	}

}
