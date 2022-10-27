package kr.co.seoulit.logistics.prodcsvc.quality.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.ui.ModelMap;

import kr.co.seoulit.logistics.prodcsvc.quality.to.ProductionPerformanceInfoTO;
import kr.co.seoulit.logistics.prodcsvc.quality.to.WorkOrderInfoTO;

public interface QualityService {

	public ModelMap getWorkOrderableMrpList();

//	public ModelMap getWorkOrderSimulationList(String mrpGatheringNoList,String mrpNoList);
public HashMap<String, Object> getWorkOrderSimulationList(String mrpNo);

	public ModelMap workOrder(String mrpGatheringNo,String workPlaceCode,String productionProcess,String mrpNo);

	public ArrayList<WorkOrderInfoTO> getWorkOrderInfoList();

	public HashMap<String, Object> workOrderCompletion(String workOrderNo,String actualCompletionAmount);

	public ArrayList<ProductionPerformanceInfoTO> getProductionPerformanceInfoList();

	public ModelMap showWorkSiteSituation(String workSiteCourse,String workOrderNo,String itemClassIfication);

	public void workCompletion(String workOrderNo,String itemCode, ArrayList<String> itemCodeListArr);

	public HashMap<String, Object> workSiteLogList(String workSiteLogDate);


}

