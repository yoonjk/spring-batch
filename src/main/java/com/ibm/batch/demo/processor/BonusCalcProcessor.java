package com.ibm.batch.demo.processor;

import org.springframework.batch.item.ItemProcessor;

import com.ibm.batch.demo.domain.Bonus;
import com.ibm.batch.demo.domain.Emp;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is for calculating a bonus of employee.
 * <li>
 *     <ol>Grade 1: Pays price of FIXED_BONUS as a bonus.</ol>
 *     <ol>From Grade2 to Grade 5: Pays calculating value which are combine BASIC_SALARY and BONUS_MAGNIFICATION.</ol>
 * </li>
 */
@Slf4j
public class BonusCalcProcessor implements ItemProcessor<Emp, Bonus> {

    @Override
    public Bonus process(Emp emp) throws Exception {
    	
        log.info("item:{}", emp);
        
        if (emp == null) {
            throw new IllegalArgumentException("emp must not be null.");
        }
        final Bonus bonus = new Bonus();
        bonus.setEmpId(emp.getEmpId());
        bonus.setPayments(calcPaymeents(emp));
        
        log.info("item:{}", emp);
        return bonus;
    }

    private Integer calcPaymeents(Emp emp) {
        if (emp.getGrade().getFixedBonus() != null) {
            return emp.getGrade().getFixedBonus();
        } else {
            return emp.getGrade().getBonusMagnification()
                    * emp.getBasicSalary() / 100;
        }
    }
}
