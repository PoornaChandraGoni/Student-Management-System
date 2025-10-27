// ---------------- CONFIG ----------------
const API = "http://localhost:8080/api/students"; // ✅ Your backend base URL

// ---------------- DOM ELEMENTS ----------------
const form = document.getElementById("studentForm");
const nameInput = document.getElementById("name");
const emailInput = document.getElementById("email");
const tableBody = document.getElementById("studentTableBody");
const emptyMessage = document.getElementById("emptyMessage");

const editModal = document.getElementById("editModal");
const editForm = document.getElementById("editForm");
const editId = document.getElementById("editId");
const editName = document.getElementById("editName");
const editEmail = document.getElementById("editEmail");
const cancelEdit = document.getElementById("cancelEdit");

const searchInput = document.getElementById("searchInput");
const searchBtn = document.getElementById("searchBtn");
const clearSearchBtn = document.getElementById("clearSearchBtn");

const toast = document.getElementById("toast");

// ---------------- HELPERS ----------------
function showToast(msg, ms = 2500) {
  toast.textContent = msg;
  toast.classList.add("toast-visible");
  setTimeout(() => toast.classList.remove("toast-visible"), ms);
}

function openEditModal() {
  editModal.classList.remove("modal-hidden");
}
function closeEditModal() {
  editModal.classList.add("modal-hidden");
}

// ---------------- API CALLS ----------------
/*async function fetchStudents() {
  const res = await fetch(API);
  if (!res.ok) throw new Error("Failed to load students");
  return res.json();
}  */



async function fetchStudents() {
  const query = searchInput.value.trim();
  let url = "http://localhost:8080/api/nameOrEmailOrId";

  if (query) {
    if (!isNaN(query)) {
      url += `?id=${encodeURIComponent(query)}`;
    } else {
      url += `?name=${encodeURIComponent(query)}&email=${encodeURIComponent(query)}`;
    }
  }

  try {
    const res = await fetch(url);

    if (!res.ok) {
      // 🔹 Backend returned an error (like 400)
      const err = await res.json(); // parse JSON error body
      console.log("❌ Backend error:", err);
      showToast(`⚠️ ${err.message}`); // show your error toast to user
      return []; // return empty list
    }

    return await res.json();
  } catch (e) {
    console.error("Fetch failed:", e);
    showToast("🚨 Unable to connect to server");
    return [];
  }
}




//   async function fetchStudents() {
//   const query = searchInput.value.trim();

//   // Build URL dynamically based on query
//   let url = "http://localhost:8080/api/nameOrEmailOrId";
//   if (query) {
//     // Send both name and email as same query for simplicity
//     url += `?name=${encodeURIComponent(query)}&email=${encodeURIComponent(query)}&id=${encodeURIComponent(query)}`;
//   }

//   const res = await fetch(url);
//   if (!res.ok) throw new Error("Failed to fetch students");
//   return await res.json();
// }


async function createStudent(data) {
  const res = await fetch(API, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  });
  if (!res.ok) throw new Error("Create failed");
  return res.json();
}

async function updateStudent(id, data) {
  const res = await fetch(`${API}/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(data)
  });
  if (!res.ok) throw new Error("Update failed");
  return res.json();
}

async function deleteStudentById(id) {
  const res = await fetch(`${API}/${id}`, { method: "DELETE" });
  if (!res.ok) throw new Error("Delete failed");
}

// ---------------- RENDER FUNCTION ----------------
function renderList(students) {
  tableBody.innerHTML = "";

  if (!students || students.length === 0) {
    emptyMessage.style.display = "block";
    return;
  }

  emptyMessage.style.display = "none";

  students.forEach((student) => {
    const tr = document.createElement("tr");

    tr.innerHTML = `
      <td>${student.id}</td>
      <td>${student.name}</td>
      <td>${student.email}</td>
      <td>
        <button class="button btn-edit" onclick="openForEdit(${student.id}, '${student.name}', '${student.email}')">Edit</button>
        <button class="button btn-delete" onclick="onDelete(${student.id})">Delete</button>
      </td>
    `;

    tableBody.appendChild(tr);
  });
}

// ---------------- MAIN OPERATIONS ----------------
/* async function loadAndRender() {
  try {
    const students = await fetchStudents();

    const query = searchInput.value.trim().toLowerCase();
    const filtered = query
      ? students.filter(
          (s) =>
            s.name.toLowerCase().includes(query) ||
            s.email.toLowerCase().includes(query)
        )
      : students;

    renderList(filtered);
  } catch (err) {
    console.error(err);
    showToast("Could not load students");
  }
} --!>*/

async function loadAndRender() {
  try {
    const students = await fetchStudents();
    renderList(students);
  } catch (err) {
    console.error(err);
    showToast("Could not load students");
  }
}




// ---------------- FORM HANDLERS ----------------
form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const payload = {
    name: nameInput.value.trim(),
    email: emailInput.value.trim()
  };

  if (!payload.name || !payload.email) {
    showToast("Please fill name and email");
    return;
  }

  try {
    await createStudent(payload);
    showToast("✅ Student added successfully");
    form.reset();
    loadAndRender();
  } catch (err) {
    console.error(err);
    showToast("Add failed");
  }
});

function openForEdit(id, name, email) {
  editId.value = id;
  editName.value = name;
  editEmail.value = email;
  openEditModal();
}

editForm.addEventListener("submit", async (e) => {
  e.preventDefault();

  const id = editId.value;
  const payload = {
    name: editName.value.trim(),
    email: editEmail.value.trim()
  };

  try {
    await updateStudent(id, payload);
    showToast("✅ Updated successfully");
    closeEditModal();
    loadAndRender();
  } catch (err) {
    console.error(err);
    showToast("Update failed");
  }
});

cancelEdit.addEventListener("click", closeEditModal);

async function onDelete(id) {
  if (!confirm("Are you sure you want to delete this student?")) return;

  try {
    await deleteStudentById(id);
    showToast("🗑️ Student deleted");
    loadAndRender();
  } catch (err) {
    console.error(err);
    showToast("Delete failed");
  }
}

searchBtn.addEventListener("click", loadAndRender);
clearSearchBtn.addEventListener("click", () => {
  searchInput.value = "";
  loadAndRender();
});

// ---------------- INITIAL LOAD ----------------
loadAndRender();
