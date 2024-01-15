import { useEffect, useState } from "react";
import "./App.css";
import UserRow from "./components/NewRow";
import NewUser from "./components/NewUser";

function App() {
  const [users, setUsers] = useState([]);
  const [newUsers, setNewUsers] = useState([]);
  const [newUserMaxID, setnewUserMaxID] = useState(0);

  useEffect(() => {
    fetch(window._env_.REACT_APP_API_URL + "/users")
      .then((response) => response.json())
      .then((data) => {
        for (const element of data) {
          element["viewMode"] = true;
        }
        setUsers(data);
      });
  }, []);

  function deleteUser(value) {
    fetch(window._env_.REACT_APP_API_URL + "/delete-user?id=" + value.id, {
      method: "DELETE",
    }).then(() => {
      setUsers((oldValues) => {
        return oldValues.filter((user) => user !== value);
      });
    });
  }

  function editModeChange(id) {
    console.log("Changing edit mode");
    for (const element of users) {
      if (element.id == id) {
        element["viewMode"] = !element["viewMode"];
      }
    }
    setUsers((prevUsers) => [...prevUsers]);
  }

  function updateUser(id, name, email, status) {
    let userForUpdate = { name: name, email: email, status: status };
    fetch(window._env_.REACT_APP_API_URL + "/update-user?id=" + id, {
      method: "PUT",
      body: JSON.stringify(userForUpdate),
    }).then(() => {
      for (const element of users) {
        if (element.id == id) {
          element.name = name;
          element.email = email;
          element.status = status;
          editModeChange(id);
          break;
        }
      }
    });
  }

  function addNewUserInput() {
    setNewUsers((newUsers) => [...newUsers, newUserMaxID]);
    setnewUserMaxID(() => newUserMaxID + 1);
  }

  function deleteNewUser(id) {
    setNewUsers((oldValues) => {
      return oldValues.filter((user) => user !== id);
    });
  }

  function updateNewUser(newUser) {
    setUsers([...users, newUser]);
  }

  return (
    <div className="App">
      {/* <header className="App-header">
        <p>User Dashboard Wiki2</p>
      </header> */}
      <div>
        {users.map((user) => (
          <div key={user.id}>
            <UserRow
              user={user}
              deleteUser={deleteUser}
              editModeChange={editModeChange}
              updateUser={updateUser}
            />
          </div>
        ))}
      </div>
      {newUsers.map((newUserID) => (
        <div key={newUserID}>
          <NewUser
            id={newUserID}
            deleteUser={deleteNewUser}
            updateNewUser={updateNewUser}
          ></NewUser>
        </div>
      ))}
      <div>
        <button onClick={() => addNewUserInput()}>New User</button>
      </div>
    </div>
  );
}

export default App;