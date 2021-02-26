const TIMEOUT = 5000;

const SPA_SETUP = {
  init: function () {
    // Render buddyinfoes in place
    $("body").append('<div id="spa"></div>');
    $(document).on("click", "#addressBookList a", function () {
      $("#spa").empty();
      $.ajax({
        type: "GET",
        url: `/addressbook/${$(this).attr("href").split("/").pop()}`,
        timeout: TIMEOUT,
        success: function (data, requestStatus, xhrObject) {
          $("#spa").append(
            `<hr/>
            <h1 th:id="${data.id}">AddressBook ${data.id}</h1>
            <table>
                <thead>
                <tr>
                    <th> Name </th>
                    <th> Phone Number </th>
                    <th> Address </th>
                </tr>
                </thead>
                <tbody id="buddyInfoList">
                </tbody>
            </table>

            <br/>
            <br/>

            <form id="${data.id}">
                <label for="name">Name:</label><br/>
                <input type="text" id="name" name="name" th:field="*{name}"><br/>

                <label for="phoneNumber">Phone Number:</label><br/>
                <input type="text" id="phoneNumber" name="phoneNumber" th:field="*{phoneNumber}"><br/>

                <label for="address">Address:</label><br/>
                <input type="text" id="address" name="address" th:field="*{address}"><br/>

                <br/>
                <button type="submit">Add new BuddyInfo</button>
            </form>
            `
          );

          for (let buddyInfo of data.list) {
            $("#buddyInfoList").append(`
            <tr id=${buddyInfo.id}>
                <td>${buddyInfo.name}</td>
                <td>${buddyInfo.phoneNumber}</td>
                <td>${buddyInfo.address}</td>
                <td>
                  <button id="${data.id}:${buddyInfo.id}" type="submit">Delete</button>
                </td>
            </tr>
            `);
          }
        },
        error: function (xhrObj, textStatus, exception) {
          alert("An error has occured");
        },
      });

      return false;
    });

    // Adding addressbook
    $("#addAddressBook").submit(function () {
      $("#spa").empty();
      $.ajax({
        type: "POST",
        url: "/addressbook/new",
        timeout: TIMEOUT,
        success: function (data, requestStatus, xhrObject) {
          $("#addressBookList").append(
            `<p><a href="/addressbook/list/${data.id}">AddressBook ${data.id}</a></p>`
          );
        },
        error: function (xhrObj, textStatus, exception) {
          alert("An error has occured");
        },
      });
      return false;
    });

    // Adding buddyinfo
    $(document).on("submit", "#spa form", function () {
      let buddyInfo = {
        name: $("#name").val(),
        phoneNumber: $("#phoneNumber").val(),
        address: $("#address").val(),
      };

      const addressbookId = $(this).attr("id");

      $.ajax({
        type: "POST",
        contentType: "application/json",
        url: `/addressbook/${addressbookId}/addBuddy`,
        timeout: TIMEOUT,
        success: function (data, requestStatus, xhrObject) {
          $("#buddyInfoList").append(`
            <tr id="${data.id}">
                <td>${data.name}</td>
                <td>${data.phoneNumber}</td>
                <td>${data.address}</td>
                <td>
                  <button id="${addressbookId}:${data.id}" type="button">Delete</button>
                </td>
            </tr>
        `);

          // Clear the inputs
          $("#name").val("");
          $("#phoneNumber").val("");
          $("#address").val("");
        },
        error: function (xhrObj, textStatus, exception) {
          alert("An error has occured");
        },
        dataType: "json",
        data: JSON.stringify(buddyInfo),
      });

      return false;
    });

    // Register the delete handler
    $(document).on("click", "#buddyInfoList button", function () {
      const ids = $(this).attr("id").split(":");

      $.ajax({
        type: "DELETE",
        url: `/addressbook/${ids[0]}/removeBuddy/${ids[1]}`,
        timeout: TIMEOUT,
        success: function (data, requestStatus, xhrObject) {
          $(`#${data.id}`).remove();
        },
        error: function (xhrObj, textStatus, exception) {
          alert("An error has occured");
        },
      });

      return false;
    });
  },
};

$(SPA_SETUP.init);
