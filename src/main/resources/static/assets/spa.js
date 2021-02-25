const TIMEOUT = 5000;

const VIEWS = {
  home: "home",
  addressBook: "addresBook",
  error: "error",
};

const RENDERER = {
  home: renderHome,
  addresBook: renderAddressBook,
  error: renderError,
};

$(document).ready(function () {
  // Default page is home
  fetchHome();
});

function renderView(view, options) {
  // Delete the current view before switching
  getApp().empty();

  if (view === VIEWS.home) {
    RENDERER.home(options);
  } else if (view === VIEWS.addressBook) {
    RENDERER.addresBook(options);
  } else if (view === VIEWS.error) {
    RENDERER.error(options);
  }
}

function renderHome({ data }) {
  const home = `
        <h1>AddressBooks:</h1>
        <div id="addressBookList">
        </div>
        </br>
        </br>
        <button id="createAddressBook">Create a new AddressBook</button>
    `;

  getApp().append(home);

  $("#createAddressBook").on("click", function () {
    $.ajax({
      type: "POST",
      url: "/addressbook/new",
      timeout: TIMEOUT,
      success: function (data, requestStatus, xhrObject) {
        renderView(VIEWS.addressBook, {
          addressBook: data,
          requestStatus,
          xhrObject,
        });
      },
      error: function (xhrObj, textStatus, exception) {
        renderView(VIEWS.error, { xhrObj, textStatus, exception });
      },
    });

    return false;
  });

  for (let addressBook of data) {
    $("#addressBookList").append(
      `<a id="addressBook${addressBook.id}" href="#">AddressBook ${addressBook.id}</a></br>`
    );
    $(`#addressBook${addressBook.id}`).on("click", function () {
      renderView(VIEWS.addressBook, { addressBook });
      return false;
    });
  }
}

function renderAddressBook({ addressBook }) {
  const addresBookView = `
        <a id="returnLink" href="#">Return to home</a>
        <h1>AddressBook ${addressBook.id}</h1>
        <table>
            <thead>
                <tr>
                    <th> Name </th>
                    <th> Phone Number </th>
                    <th> Address </th>
                </tr>
            </thead>
            <tbody id="list">
                
            </tbody>
        </table>
        </br>
        </br>
        <p>Name</p>
        <input id="name" type="text" />
        <p>Phone Number</p>
        <input id="phoneNumber" type="text" />
        <p>Address</p>
        <input id="address" type="text" />
        </br>
        </br>
        <button id="addBuddyInfo" type="button">Add a new BuddyInfo</button>
    `;

  getApp().append(addresBookView);

  for (let buddyInfo of addressBook.list) {
    $("#list").append(`
            <tr>
                <td>${buddyInfo.name}</td>
                <td>${buddyInfo.phoneNumber}</td>
                <td>${buddyInfo.address}</td>
                <td>
                    <button id="deleteBuddyInfo${buddyInfo.id}" type="button">Delete</button>
                </td>
            </tr>
        `);

    $(`#deleteBuddyInfo${buddyInfo.id}`).on("click", function () {
      $.ajax({
        type: "DELETE",
        url: `/addressbook/${addressBook.id}/removeBuddy/${buddyInfo.id}`,
        timeout: TIMEOUT,
        success: function (data, requestStatus, xhrObject) {
          renderView(VIEWS.addressBook, {
            addressBook: data,
            requestStatus,
            xhrObject,
          });
        },
        error: function (xhrObj, textStatus, exception) {
          renderView(VIEWS.error, { xhrObj, textStatus, exception });
        },
      });

      return false;
    });
  }

  $("#returnLink").on("click", function () {
    fetchHome();
    return false;
  });

  $("#addBuddyInfo").on("click", function () {
    let buddyInfo = {
      name: $("#name").val(),
      phoneNumber: $("#phoneNumber").val(),
      address: $("#address").val(),
    };

    $.ajax({
      type: "POST",
      contentType: "application/json",
      url: `/addressbook/${addressBook.id}/addBuddy`,
      timeout: TIMEOUT,
      success: function (data, requestStatus, xhrObject) {
        renderView(VIEWS.addressBook, {
          addressBook: data,
          requestStatus,
          xhrObject,
        });
      },
      error: function (xhrObj, textStatus, exception) {
        renderView(VIEWS.error, { xhrObj, textStatus, exception });
      },
      dataType: "json",
      data: JSON.stringify(buddyInfo),
    });

    return false;
  });
}

function renderError() {
  getApp().append(
    `<a id="returnLink" href="#">Return to home</a><h1>Error</h1><p>An error has occured</p>`
  );

  $("#returnLink").on("click", function () {
    fetchHome();
    return false;
  });
}

function fetchHome() {
  $.ajax({
    type: "GET",
    url: "/addressbook/all",
    timeout: TIMEOUT,
    success: function (data, requestStatus, xhrObject) {
      viewData = data;
      renderView(VIEWS.home, { data: viewData, requestStatus, xhrObject });
    },
    error: function (xhrObj, textStatus, exception) {
      renderView(VIEWS.error, { xhrObj, textStatus, exception });
    },
  });
}

function getApp() {
  return $("#app");
}
