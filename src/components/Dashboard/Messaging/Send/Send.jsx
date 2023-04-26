import { Autocomplete, Box, Container, FormGroup, InputLabel, Paper, Stack, TextField, Typography } from '@mui/material'
import { useEffect } from 'react'
import { useState } from 'react'
import { Form } from 'react-bootstrap'
import { MDBTable, MDBTableHead, MDBTableBody } from 'mdb-react-ui-kit';
// import { Form } from 'react-router-dom'
import { Label, Table } from 'reactstrap'
import { Pane } from 'split-pane-react'
import SplitPane from 'split-pane-react/esm/SplitPane'
import Button from "@mui/material/Button"
import './Send.css'
import { AutoComplete } from "@mui/material";
import MessageLogSummary from './MessageLogSummary/MessageLogSummary';
import { API_URL } from '../../../../Constants/Index';
import * as XLSX from 'xlsx/xlsx.mjs';
// import ReactFileInput from "react-file-input";

const senderNames = [
  { label: 'Test1' },
  { label: 'Test2' },
];

const Send = ({ token }) => {

  // handle file

  const [data, setData] = useState([]);

  const handleFileUpload = (event) => {
    const file = event.target.files[0];
    const reader = new FileReader();
    reader.onload = (event) => {
      const binaryData = event.target.result;
      const workbook = XLSX.read(binaryData, { type: 'binary' });
      const sheetName = workbook.SheetNames[0];
      const worksheet = workbook.Sheets[sheetName];
      const columnData = XLSX.utils.sheet_to_json(worksheet, { header: 1 })[0];
      const filteredData = columnData.filter((cell) => !isNaN(cell));
      setData(filteredData);
    };
    reader.readAsBinaryString(file);
  };
  // end handleFile function

  const [counter, setCounter] = useState([0])
  const [recipients, setRecipients] = useState()

  const [sender, setSender] = useState()
  const [selectedSenderName, setSelectedSenderName] = useState('')
  const [message, setMessage] = useState()

  let senderName_requestOptions = {
    method: "GET",
    headers: {
      'Authorization': `Token ${token}`
    },
    redirect: 'follow'
  }

  // for getting valid json list
  function convertToJsonList(response) {
    let json_string = JSON.stringify(response[0]).replace('success', '"success"');
    // json_string = json_string.replace("msg", "'msg'");
    let data = json_string.startsWith("'");
    data = json_string.endsWith("'");
    data = json_string.substring(1, json_string.length - 1);
    data = data.replace(/\\/g, '');
    console.log(data);
    return JSON.parse(data)
  }

  useEffect(() => {

    fetch(API_URL + "getsendernames", senderName_requestOptions)
      .then(response => response.json())
      .then(result => setSender(convertToJsonList(result)))
      .catch(error => console.log('error', error))
  }, [])

  function handleSelect(event) {
    setSelectedSenderName(event.target.value)
  }

  function send_message() {


  }
  const [file, setFile] = useState(null);
  const [values, setValues] = useState([]);

  const handleChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (!file) {
      return;
    }

    const reader = new FileReader();
    reader.onload = (e) => {
      const contents = e.target.result;
      const rows = contents.split("\n");
      const firstColumn = rows.map(row => row.split(",")[0]);
      setValues(firstColumn);
    };
    reader.readAsText(file);
  };



  return (
    <div className='send-container'>
      <div className='send-form' style={{ width: '40%' }}>

        {/* <Box sx={{ width: "30%" }}> */}
        <div className="send-headers">
          <span>Compose Message</span>
          <hr />
        </div>

        <Stack direction="row" gap={1} margin="1rem" marginTop={1}>

          <button className='app-buttons'>Add Contact(s)</button>
          <button className='app-buttons'>Add Group(s)</button>

        </Stack>
        <Box>
          {/* <Form sx={{ padding: "1rem" }}> */}
          <form encType='multipart/form-data' onSubmit={send_message}>
            <div className="form-outline">
              <label className="form-label" for="textAreaExample">Phone Number</label>
              <textarea
                className="form-control form-control-sm"
                id="textAreaExample"
                rows="4"
                onChange={(e) => setRecipients(e.target.value)}
                value={recipients}
              />
            </div>

            <div className="form-outline">
              <label className="form-label" >Load Contact File(Excel Format)</label>
              {/* <input type="file" id="formControlSm" className="form-control form-control-sm" onChange={handleFileUpload} />
              <ul>
                {data.map((cell, index) => (
                  <li key={index}>{cell}</li>
                ))}
              </ul> */}

              {/* <ReactFileInput
                type="file"
                onChange={handleChange}
              /> */}
              <input
                type="text"
                value={values}
                readOnly
              />
            </div>

            <div className="form-outline">
              <label className='form-label'>Sender Name</label>

            </div>
            <select className="form-select" onChange={handleSelect} required>
              <option value="">Select an Sender Name</option>
              {sender && sender.response.map(sendername => (
                <option key={sendername.fields.sender.fields.sendername} value={sendername.fields.sender.fields.sendername}>
                  {sendername.fields.sender.fields.sendername}
                </option>
              ))}
            </select>


            <div className="form-outline">
              <label className="form-label" for="textAreaExample">Message</label>
              <textarea
                className="form-control form-control-sm"
                id="textAreaExample"
                rows="4"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
              />
              <span>{counter} characters of message 1</span>
            </div>

            <FormGroup>
              <button className='app-buttons' color='secondary' disabled >Send</button>
            </FormGroup>
            {/* </Form> */}
          </form>

        </Box>
        {/* </Box> */}


      </div>


      <div className='right-side'>
        <div className='message-log-table'>
          {/* <MessageLogSummary token={token}/> */}
        </div>
        <div className='message-log-table'>
          <MDBTable className='caption-top'>
            <caption>List of users</caption>
            <MDBTableHead>
              <tr>
                <th scope='col'>#</th>
                <th scope='col'>First</th>
                <th scope='col'>Last</th>
                <th scope='col'>Handle</th>
              </tr>
            </MDBTableHead>
            <MDBTableBody>
              <tr>
                <th scope='row'>1</th>
                <td>Mark</td>
                <td>Otto</td>
                <td>@mdo</td>
              </tr>
              <tr>
                <th scope='row'>2</th>
                <td>Jacob</td>
                <td>Thornton</td>
                <td>@fat</td>
              </tr>
              <tr>
                <th scope='row'>3</th>
                <td>Larry</td>
                <td>the Bird</td>
                <td>@twitter</td>
              </tr>
            </MDBTableBody>
          </MDBTable>
        </div>


      </div>



    </div>

  )
}

export default Send
