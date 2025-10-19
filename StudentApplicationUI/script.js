const apiUrl = "http://localhost:8080/api/students";
const form = document.getElementById("studentForm");
const tableBody = document.getElementById("studentTable");

// Fetch and display students on load
window.onload = loadStudents;

// Add new student
form.addEventListener("submit", (e) => {
  e.preventDefault();
  const name = document.getElementById("name").value;
  const email = document.getElementById("email").value;

  fetch(apiUrl, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, email }),
  })
    .then((res) => res.json())
    .then(() => {
      loadStudents(); // reload table
      form.reset();
    });
});

// Load all students
function loadStudents() {
  fetch(apiUrl)
    .then((res) => res.json())
    .then((data) => {
      tableBody.innerHTML = "";
      data.forEach((student) => {
        const row = `<tr>
          <td>${student.id}</td>
          <td>${student.name}</td>
          <td>${student.email}</td>
          <td>
            <button onclick="deleteStudent(${student.id})">Delete</button>
          </td>
        </tr>`;
        tableBody.innerHTML += row;
      });
    });
}

// Delete student
function deleteStudent(id) {
  fetch(`${apiUrl}/${id}`, { method: "DELETE" })
    .then(() => loadStudents());
}
