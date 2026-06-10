loadComplaints();

function loadComplaints() {
    fetch("http://localhost:8080/api/complaints")
        .then(response => response.json())
        .then(data => displayComplaints(data));
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
            ? new Date(complaint.createdAt).toLocaleDateString()
            : ""}
            </td>

            <td>
                <button onclick="updateStatus(${complaint.id}, 'IN_PROGRESS')">In Progress</button>
                <button onclick="updateStatus(${complaint.id}, 'RESOLVED')">Resolve</button>
                <button onclick="updateStatus(${complaint.id}, 'REJECTED')">Reject</button>
                <button onclick="deleteComplaint(${complaint.id})">Delete</button>
            </td>
        </tr>
        `;
    });

    document.getElementById("complaintsTable").innerHTML = rows;
}

function filterByStatus(status) {
    fetch("http://localhost:8080/api/complaints")
        .then(response => response.json())
        .then(data => {
            const filtered = data.filter(c => c.status === status);
            displayComplaints(filtered);
        });
}

function searchComplaints() {
    const keyword =
        document.getElementById("searchBox").value.toLowerCase();

    fetch("http://localhost:8080/api/complaints")
        .then(response => response.json())
        .then(data => {
            const filtered = data.filter(c =>
                c.title &&
                c.title.toLowerCase().includes(keyword)
            );
            displayComplaints(filtered);
        });
}

function updateStatus(id, status) {

    fetch(`http://localhost:8080/api/complaints/${id}/status?status=${status}`, {
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

function deleteComplaint(id) {

    if (confirm("Are you sure you want to delete this complaint?")) {

        fetch(`http://localhost:8080/api/complaints/${id}`, {
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

function getStatusClass(status) {
    if (status === "PENDING") return "pending";
    if (status === "IN_PROGRESS") return "progress";
    if (status === "RESOLVED") return "resolved";
    return "rejected";
}