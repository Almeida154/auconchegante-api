<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Password Reset</title>
    <link rel="preconnect" href="https://fonts.googleapis.com" />
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin />
    <link
      href="https://fonts.googleapis.com/css2?family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap"
      rel="stylesheet"
    />
    <style>
      * {
        font-family: "Montserrat", Arial, sans-serif;
      }
      span {
        font-weight: 600;
      }
    </style>
  </head>
  <body>
    <table
      border="0"
      cellpadding="0"
      cellspacing="0"
      height="100%"
      width="100%"
      style="padding: 40px"
    >
      <tbody>
        <tr>
          <td align="center" valign="top" style="border-collapse: collapse">
            <table
              border="0"
              cellpadding="0"
              cellspacing="0"
              width="100%"
              style="
                background-color: #fff;
                background-image: none;
                background-repeat: repeat;
                background-position: top left;
              "
            >
              <tbody>
                <tr>
                  <td
                    align="center"
                    valign="top"
                    style="border-collapse: collapse"
                  >
                    <table
                      border="0"
                      cellpadding="0"
                      cellspacing="0"
                      height="90"
                      width="100%"
                      style="
                        background-color: #ffffff;
                        background-image: none;
                        background-repeat: repeat;
                        background-position: top left;
                      "
                    >
                      <tbody>
                        <tr>
                          <td
                            align="center"
                            valign="middle"
                            style="border-collapse: collapse"
                          >
                            <img
                              src="https://raw.githubusercontent.com/Almeida154/auconchegante-api/df7e50d08ded31385ca05f92bad4d454d16bd939/.github/logo.png"
                              width="200"
                              alt="Auconchegante"
                            />
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td
                    align="center"
                    valign="top"
                    style="border-collapse: collapse"
                  >
                    <table
                      border="0"
                      cellpadding="0"
                      cellspacing="0"
                      height="1"
                      width="100%"
                    >
                      <tbody>
                        <tr>
                          <td
                            align="center"
                            valign="middle"
                            style="
                              border-collapse: collapse;
                              background-color: #eeeeee;
                            "
                            width="249"
                          ></td>
                          <td
                            align="center"
                            valign="middle"
                            style="
                              border-collapse: collapse;
                              background-color: #23b2ff;
                            "
                            width="80"
                          ></td>
                          <td
                            align="center"
                            valign="middle"
                            style="
                              border-collapse: collapse;
                              background-color: #eeeeee;
                            "
                            width="249"
                          ></td>
                        </tr>
                      </tbody>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td
                    align="center"
                    valign="top"
                    style="border-collapse: collapse"
                  >
                    <table
                      border="0"
                      cellpadding="40"
                      cellspacing="0"
                      height="0"
                      width="100%"
                    >
                      <tbody>
                        <tr>
                          <td
                            align="center"
                            valign="middle"
                            style="
                              border-collapse: collapse;
                              font-size: 24px;
                              text-align: center;
                            "
                          >
                            Hi, ${name}. Did you want to
                            <span>reset</span> your <span>password</span>?
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td
                    align="center"
                    valign="top"
                    style="border-collapse: collapse"
                  >
                    <table
                      border="0"
                      cellpadding="0"
                      cellspacing="0"
                      height="0"
                      width="100%"
                    >
                      <tbody>
                        <tr>
                          <td
                            align="center"
                            valign="middle"
                            style="border-collapse: collapse"
                          >
                            <div
                              style="
                                text-align: left;
                                padding: 0 20px 20px;
                                font-size: 14px;
                                line-height: 1.5;
                                width: 80%;
                              "
                            >
                              <p style="text-align: center">
                                Someone (hopefully you) has asked us to
                                <span>reset</span> the <span>password</span> for
                                your Auconchegante account. Please, use the
                                <span>${expirationMinutes} minutes</span> valid
                                code below to do so. If you didn't request this
                                <span>password reset</span>, you can go ahead
                                and ignore this email!
                              </p>
                            </div>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </td>
                </tr>
                <tr>
                  <td
                    align="center"
                    valign="top"
                    style="border-collapse: collapse"
                  >
                    <table
                      border="0"
                      cellpadding="30"
                      cellspacing="0"
                      height="0"
                      width="100%"
                      style="
                        border-top-width: 1px;
                        border-top-style: solid;
                        border-top-color: #eee;
                      "
                    >
                      <tbody>
                        <tr>
                          <td
                            align="center"
                            valign="middle"
                            style="border-collapse: collapse"
                          >
                            <div
                              style="
                                padding: 2px 32px;
                                background-color: #f6f6f6;
                                border-radius: 8px;
                                color: #23b2ff;
                                font-size: 24px;
                                font-weight: 600;
                                width: fit-content;
                                letter-spacing: 4px;
                              "
                            >
                              <p>${code}</p>
                            </div>
                          </td>
                        </tr>
                      </tbody>
                    </table>
                  </td>
                </tr>
              </tbody>
            </table>
          </td>
        </tr>
      </tbody>
    </table>
  </body>
</html>
