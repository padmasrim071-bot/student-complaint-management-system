// ===============================
// Admin Dashboard Statistics + Pie Chart
// ===============================

const statsBox = document.getElementById("stats");
const chartBox = document.getElementById("complaintChart");

if (statsBox && chartBox) {
    fetch("/api/complaints/dashboard")
        .then(response => response.json())
        .then(data => {

            statsBox.innerHTML = `
                <b>Total:</b> ${data.totalComplaints}<br>
                <b>Pending:</b> ${data.pending}<br>
                <b>Resolved:</b> ${data.resolved}<br>
                <b>In Progress:</b> ${data.inProgress}<br>
                <b>Rejected:</b> ${data.rejected}
            `;

            new Chart(chartBox, {
                type: "pie",
                data: {
                    labels: [
                        "Pending",
                        "Resolved",
                        "In Progress",
                        "Rejected"
                    ],
                    datasets: [{
                        data: [
                            data.pending,
                            data.resolved,
                            data.inProgress,
                            data.rejected
                        ],
                        backgroundColor: [
                            "#f59e0b",
                            "#10b981",
                            "#3b82f6",
                            "#ef4444"
                        ]
                    }]
                },
                options: {
                    responsive: true,
                    maintainAspectRatio: false
                }
            });
        });
}

// ===============================
// Admin Complaints Page
// ===============================

if (document.getElementById("complaintsTable")) {
    loadComplaints();
}

function loadComplaints() {
    fetch("/api/complaints")

        .then(response => response.json())
        .then(data => {
            displayComplaints(data);
        });
}

function displayComplaints(data) {
    let rows = "";

    data.forEach(complaint => {
        rows += `
            <tr>
                <td>${complaint.id}</td>
                <td>${complaint.title || ""}</td>
                <td>${complaint.description || ""}</td>
                <td>${complaint.category || "-"}</td>

                <td>
                    <span class="${getStatusClass(complaint.status)}">
                        ${complaint.status}
                    </span>
                </td>

                <td>
                    ${complaint.createdAt
            ? complaint.createdAt.substring(0, 10)
            : ""}
                </td>

                <td>
                    <button onclick="updateStatus(${complaint.id}, 'IN_PROGRESS')">
                        In Progress
                    </button>

                    <button onclick="updateStatus(${complaint.id}, 'RESOLVED')">
                        Resolve
                    </button>

                    <button onclick="updateStatus(${complaint.id}, 'REJECTED')">
                        Reject
                    </button>

                    <button onclick="deleteComplaint(${complaint.id})">
                        Delete
                    </button>
                </td>
            </tr>
        `;
    });

    document.getElementById("complaintsTable").innerHTML = rows;
}

// ===============================
// Filter By Status
// ===============================

function filterByStatus(status) {
    fetch("/api/complaints")
        .then(response => response.json())
        .then(data => {
            const filtered =
                data.filter(complaint => complaint.status === status);

            displayComplaints(filtered);
        });
}

// ===============================
// Search Complaints
// ===============================

function searchComplaints() {
    const keyword =
        document.getElementById("searchBox").value.toLowerCase();

    fetch("/api/complaints")
        .then(response => response.json())
        .then(data => {
            const filtered = data.filter(complaint =>
                complaint.title &&
                complaint.title.toLowerCase().includes(keyword)
            );

            displayComplaints(filtered);
        });
}

// ===============================
// Update Status
// ===============================

function updateStatus(id, status) {
    fetch(`/api/complaints/${id}/status?status=${status}`, {
        method: "PUT"
    })
        .then(response => {
            if (response.ok) {
                alert("Complaint status updated successfully!");
                loadComplaints();
            } else {
                alert("Failed to update status.");
            }
        });
}

// ===============================
// Delete Complaint
// ===============================


function deleteComplaint(id) {
    if (confirm("Are you sure you want to delete this complaint?")) {
        fetch(`/api/complaints/${id}`, {
            method: "DELETE"
        })
            .then(response => {
                if (response.ok) {
                    alert("Complaint deleted successfully!");
                    loadComplaints();
                } else {
                    alert("Failed to delete complaint.");
                }
            });
    }
}
// ===============================
// Status Color Class
// ===============================

function getStatusClass(status) {
    if (status === "PENDING") {
        return "pending";
    }

    if (status === "IN_PROGRESS") {
        return "progress";
    }

    if (status === "RESOLVED") {
        return "resolved";
    }

    return "rejected";
}