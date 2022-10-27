package kr.co.seoulit.logistics.prodcsvc.production.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import kr.co.seoulit.logistics.busisvc.sales.controller.logisales.to.ContractDetailInMpsAvailableTO;
import kr.co.seoulit.logistics.prodcsvc.production.service.ProductionService;
import kr.co.seoulit.logistics.prodcsvc.production.to.MpsTO;
import kr.co.seoulit.logistics.prodcsvc.production.to.SalesPlanInMpsAvailableTO;

import kr.co.seoulit.logistics.sys.response.Response;
import static kr.co.seoulit.logistics.sys.response.Response.success;

@CrossOrigin
@SuppressWarnings("unused")
@RestController
@RequestMapping("/production/*")
public class MpsController {

	@Autowired
	private ProductionService productionService;

	private ModelMap modelMap = new ModelMap();

	ModelMap map = null;

	private static Gson gson = new GsonBuilder().serializeNulls().create();

	//조회
	@RequestMapping(value="/mps/list", method=RequestMethod.GET)
	public ModelMap searchMpsInfo(HttpServletRequest request, HttpServletResponse response) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String includeMrpApply = request.getParameter("includeMrpApply");
		map = new ModelMap();
		try {
			ArrayList<MpsTO> mpsTOList = productionService.getMpsList(startDate, endDate, includeMrpApply);

			map.put("gridRowJson", mpsTOList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}


	//가능 조회
	@RequestMapping(value="/mps/contractdetail-available", method=RequestMethod.GET)
	public ModelMap searchContractDetailListInMpsAvailable(HttpServletRequest request,
			HttpServletResponse response) {
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String searchCondition = request.getParameter("searchCondition");
		map = new ModelMap();
		try {
			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList =
					productionService.getContractDetailListInMpsAvailable(searchCondition, startDate, endDate);
			map.put("gridRowJson", contractDetailInMpsAvailableList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
			System.out.println("데이터가 도착");
			System.out.println("MAP-----------" + map);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value="/mps/contractdetail-processplanavailable", method=RequestMethod.GET)
	public ModelMap searchContractDetailListInProcessPlanAvailable(HttpServletRequest request,
														   HttpServletResponse response) {
		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		map = new ModelMap();
		try {
			ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList =
					productionService.getContractDetailListInProcessPlanAvailable(searchCondition, startDate, endDate);
			map.put("gridRowJson", contractDetailInMpsAvailableList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value="/mps/salesplan-available", method=RequestMethod.GET)
	public ModelMap searchSalesPlanListInMpsAvailable(HttpServletRequest request, HttpServletResponse response) {
		String searchCondition = request.getParameter("searchCondition");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		map = new ModelMap();
		try {
			ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList =
					productionService.getSalesPlanListInMpsAvailable(searchCondition, startDate, endDate);

			map.put("gridRowJson", salesPlanInMpsAvailableList);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}

	@RequestMapping(value="mps/contractdetail", method=RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Response convertContractDetailToMps(@RequestBody ContractDetailInMpsAvailableTO contract) {

		System.out.println("MPS등록 전달 확인");

		System.out.println("MPS등록할 값:::::::::::::::" + contract);
//		map = new ModelMap();
//		ArrayList<ContractDetailInMpsAvailableTO> contractDetailInMpsAvailableList = gson.fromJson(batchList,
//				new TypeToken<ArrayList<ContractDetailInMpsAvailableTO>>() {}.getType());

		try {
			System.out.println("contract.getPlanClassification()");
			System.out.println(contract.getPlanClassification());
			HashMap<String, Object> resultMap = productionService.convertContractDetailToMps(contract);

			modelMap.put("result", resultMap);

		} catch (Exception e2) {
			e2.printStackTrace();
			modelMap.put("errorCode", -2);
			modelMap.put("errorMsg", e2.getMessage());

		}
		return success(modelMap);
	}

	@RequestMapping(value="/mps/salesplan", method=RequestMethod.PUT)
	public ModelMap convertSalesPlanToMps(HttpServletRequest request, HttpServletResponse response) {
		String batchList = request.getParameter("batchList");
		map = new ModelMap();
		try {
			ArrayList<SalesPlanInMpsAvailableTO> salesPlanInMpsAvailableList = gson.fromJson(batchList,
					new TypeToken<ArrayList<SalesPlanInMpsAvailableTO>>() {
					}.getType());
			HashMap<String, Object> resultMap = productionService.convertSalesPlanToMps(salesPlanInMpsAvailableList);

			map.put("result", resultMap);
			map.put("errorCode", 1);
			map.put("errorMsg", "성공");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("errorCode", -1);
			map.put("errorMsg", e1.getMessage());
		}
		return map;
	}


}
