package org.mvc;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;

public class CutomerItemProcessor implements ItemProcessor<Customer, CustomerAfterMap> {

	@Override
	public CustomerAfterMap process(Customer item) throws Exception {
		CustomerAfterMap cuAfterMap = new CustomerAfterMap();
		BeanUtils.copyProperties(item, cuAfterMap);
		cuAfterMap.setName(item.getName() + "____Batch");

		return cuAfterMap;
	}

}
