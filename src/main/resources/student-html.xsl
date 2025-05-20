<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns2="http://viko.eif.lt/gtamaseviciute/studentWebSerice"
                version="1.0">

  <xsl:output method="html" encoding="UTF-8" indent="yes"/>

  <xsl:template match="/">
    <html>
      <head>
        <title>Students List</title>
        <style>
          body {
            font-family: Arial, sans-serif;
            background-color: #f6f8fa;
            margin: 40px;
          }
          h2 {
            text-align: center;
            color: #2c3e50;
          }
          .header-img {
            display: block;
            margin: 0 auto 20px auto;
            width: 120px;
            border-radius: 10px;
          }
          table {
            border-collapse: collapse;
            width: 100%;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            background: white;
          }
          th {
            background-color: #2980b9;
            color: white;
            padding: 12px;
            text-align: left;
          }
          td {
            padding: 10px;
            border-bottom: 1px solid #ddd;
          }
          tr:nth-child(even) {
            background-color: #f2f2f2;
          }
          .badge {
            display: inline-block;
            padding: 3px 8px;
            border-radius: 12px;
            background-color: #3498db;
            color: white;
            font-size: 0.8em;
          }
        </style>
      </head>
      <body>
        <img src="https://cdn-icons-png.flaticon.com/512/3135/3135755.png" class="header-img"/>
        <h2>📚 Students Report</h2>

        <table>
          <tr>
            <th>👤 Name</th>
            <th>🎂 Age</th>
            <th>🏷️ Group</th>
            <th>✔️ Active</th>
            <th>📊 Average</th>
            <th>⚧ Gender</th>
            <th>📘 Subjects</th>
          </tr>

          <xsl:for-each select="ns2:getStudentsByGroupResponse/ns2:students | ns2:getStudentsResponse/ns2:students | ns2:getStudentResponse/ns2:student">
            <tr>
              <td><xsl:value-of select="ns2:name"/></td>
              <td><xsl:value-of select="ns2:age"/></td>
              <td><xsl:value-of select="ns2:group"/></td>
              <td>
                <xsl:choose>
                  <xsl:when test="ns2:active='true'">✅</xsl:when>
                  <xsl:otherwise>❌</xsl:otherwise>
                </xsl:choose>
              </td>
              <td><span class="badge"><xsl:value-of select="ns2:averageGrade"/></span></td>
              <td>
                <xsl:choose>
                  <xsl:when test="ns2:gender='M'">👨</xsl:when>
                  <xsl:when test="ns2:gender='F'">👩</xsl:when>
                  <xsl:otherwise><xsl:value-of select="ns2:gender"/></xsl:otherwise>
                </xsl:choose>
              </td>
              <td>
                <xsl:for-each select="ns2:subjects">
                  <div><xsl:value-of select="ns2:title"/> (<b><xsl:value-of select="ns2:grade"/></b>)</div>
                </xsl:for-each>
              </td>
            </tr>
          </xsl:for-each>
        </table>
      </body>
    </html>
  </xsl:template>
</xsl:stylesheet>

