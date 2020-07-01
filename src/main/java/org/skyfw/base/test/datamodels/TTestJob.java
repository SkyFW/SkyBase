package org.skyfw.base.test.datamodels;

import org.skyfw.base.datamodel.TBaseDataModel;
import org.skyfw.base.datamodel.annotation.DataModel;
import org.skyfw.base.datamodel.annotation.KeyField;


@DataModel(dataStoreName= "job_table")
public class TTestJob extends TBaseDataModel {

    @KeyField
    private String jobId;

    private String jobName;

    private Integer salary;

    private TTestUser jobOwner;


    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public TTestUser getJobOwner() {
        return jobOwner;
    }

    public void setJobOwner(TTestUser jobOwner) {
        this.jobOwner = jobOwner;
    }
}



