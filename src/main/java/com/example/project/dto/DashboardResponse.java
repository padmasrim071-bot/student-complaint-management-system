package com.example.project.dto;

public class DashboardResponse {

    private long totalComplaints;
    private long pending;
    private long resolved;
    private long inProgress;
    private long rejected;

    public DashboardResponse() {
    }

    public DashboardResponse(long totalComplaints,
                             long pending,
                             long resolved,
                             long inProgress,
                             long rejected) {

        this.totalComplaints = totalComplaints;
        this.pending = pending;
        this.resolved = resolved;
        this.inProgress = inProgress;
        this.rejected = rejected;
    }

    public long getTotalComplaints() {
        return totalComplaints;
    }

    public void setTotalComplaints(long totalComplaints) {
        this.totalComplaints = totalComplaints;
    }

    public long getPending() {
        return pending;
    }

    public void setPending(long pending) {
        this.pending = pending;
    }

    public long getResolved() {
        return resolved;
    }

    public void setResolved(long resolved) {
        this.resolved = resolved;
    }

    public long getInProgress() {
        return inProgress;
    }

    public void setInProgress(long inProgress) {
        this.inProgress = inProgress;
    }

    public long getRejected() {
        return rejected;
    }

    public void setRejected(long rejected) {
        this.rejected = rejected;
    }
}