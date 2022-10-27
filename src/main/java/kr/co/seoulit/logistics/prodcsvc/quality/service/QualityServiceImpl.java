package kr.co.seoulit.logistics.prodcsvc.quality.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import kr.co.seoulit.logistics.prodcsvc.quality.mapper.WorkOrderMapper;
import kr.co.seoulit.logistics.prodcsvc.quality.to.ProductionPerformanceInfoTO;
import kr.co.seoulit.logistics.prodcsvc.quality.to.WorkOrderInfoTO;
import kr.co.seoulit.logistics.prodcsvc.quality.to.WorkSiteLog;

@Service
public class QualityServiceImpl implements QualityService {

	@Autowired
	private WorkOrderMapper workOrderMapper;

	@Override
	public ModelMap getWorkOrderableMrpList() {

		ModelMap resultMap = new ModelMap();

		HashMap<String, Object> map = new HashMap<String, Object>();

		workOrderMapper.getWorkOrderableMrpList(map);

        resultMap.put("gridRowJson", map.get("RESULT"));
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));

		return resultMap;

	}

//	@Override
//	public ModelMap getWorkOrderSimulationList(String mrpNo) {
//
//		mrpGatheringNoList=mrpGatheringNoList.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "");
////		mrpNoList=mrpNoList.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "");
//
//		ModelMap resultMap = new ModelMap();
//
//		HashMap<String, String> map = new HashMap<>();
//
//		map.put("mrpGatheringNoList", mrpGatheringNoList);
////		map.put("mrpNoList", mrpNoList);
//
//		workOrderMapper.getWorkOrderSimulationList(map);
//
//		resultMap.put("result", map.get("RESULT"));
//		resultMap.put("errorCode", map.get("ERROR_CODE"));
//		resultMap.put("errorMsg", map.get("ERROR_MSG"));
//
//		return resultMap;
//	}

	@Override
	public HashMap<String, Object> getWorkOrderSimulationList(String mrpNo) {

		HashMap<String, Object> param = new HashMap<>();
		param.put("mrpNo", mrpNo);

		workOrderMapper.getWorkOrderSimulationList(param);

		return param;

	}

	@Override
	public ModelMap workOrder(String mrpGatheringNo,String workPlaceCode,String productionProcess,String mrpNo) {

		mrpGatheringNo=mrpGatheringNo.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "");
		mrpNo=mrpNo.replace("[", "").replace("]", "").replace("{", "").replace("}", "").replace("\"", "");

		ModelMap resultMap = new ModelMap();

		HashMap<String, String> map = new HashMap<>();

		map.put("mrpGatheringNo", mrpGatheringNo);
		map.put("workPlaceCode", workPlaceCode);
		map.put("productionProcess", productionProcess);
		map.put("mrpNo", mrpNo);

		workOrderMapper.workOrder(map);

		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));

    	return resultMap;

	}

	@Override
	public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList() {

		ArrayList<WorkOrderInfoTO> workOrderInfoList = null;

		workOrderInfoList = workOrderMapper.selectWorkOrderInfoList();

		return workOrderInfoList;

	}

	@Override
	public HashMap<String,Object> workOrderCompletion(String workOrderNo,String actualCompletionAmount) {

		HashMap<String, Object> map = new HashMap<>();

		map.put("workOrderNo", workOrderNo);
		map.put("actualCompletionAmount", actualCompletionAmount);

		workOrderMapper.workOrderCompletion(map);

    	return map;

	}

	@Override
	public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList() {

		ArrayList<ProductionPerformanceInfoTO> productionPerformanceInfoList = null;

		productionPerformanceInfoList = workOrderMapper.selectProductionPerformanceInfoList();

		return productionPerformanceInfoList;

	}

	@Override
	public ModelMap showWorkSiteSituation(String workSiteCourse,String workOrderNo,String itemClassIfication) {

		HashMap<String,Object> map = new HashMap<String, Object>();

		ModelMap resultMap = new ModelMap();

		map.put("workOrderNo", workOrderNo);
		map.put("workSiteCourse", workSiteCourse);
		map.put("itemClassIfication", itemClassIfication);

		workOrderMapper.selectWorkSiteSituation(map);

		resultMap.put("gridRowJson", map.get("RESULT"));
		resultMap.put("errorCode", map.get("ERROR_CODE"));
		resultMap.put("errorMsg", map.get("ERROR_MSG"));

		return resultMap;
	}

	@Override
	public void workCompletion(String workOrderNo, String itemCode ,  ArrayList<String> itemCodeListArr) {

		String itemCodeList=itemCodeListArr.toString().replace("[", "").replace("]", "");

		HashMap<String, String> map = new HashMap<>();

		map.put("workOrderNo", workOrderNo);
		map.put("itemCode", itemCode);
		map.put("itemCodeList", itemCodeList);

		workOrderMapper.updateWorkCompletionStatus(map);

	}

	@Override
	public HashMap<String, Object> workSiteLogList(String workSiteLogDate) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("workSiteLogDate", workSiteLogDate);

		ArrayList<WorkSiteLog> list = workOrderMapper.workSiteLogList(map);
		resultMap.put("gridRowJson",list);
		resultMap.put("errorCode", 1);
		resultMap.put("errorMsg","성공");

		return resultMap;
	}

}
