import { Autocomplete, Box, Container, FormGroup, InputLabel, Paper, Stack, TextField, Typography } from '@mui/material'
import React from 'react'
import { useState } from 'react'
import { Form } from 'react-bootstrap'
import { MDBTable, MDBTableHead, MDBTableBody } from 'mdb-react-ui-kit';
// import { Form } from 'react-router-dom'
import { Label, Table } from 'reactstrap'
import { Pane } from 'split-pane-react'
import SplitPane from 'split-pane-react/esm/SplitPane'
import Button from "@mui/material/Button"
import './Send.css'
const senderNames = [
  { label: 'Test1' },
  { label: 'Test2' },
];

const Send = () => {
  const [counter, setCounter] = useState([0])
  // const [sizes, setSizes] = useState([
  //   100,
  //   '30%',
  //   '100%',
  // ]);

  // const layoutCSS = {
  //   width: '50%',
  //   height:'100%',
  //   display: 'flex',
  //   alignItems: 'center',
  //   justifyContent: 'center',
  // };

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
          <Form sx={{ padding: "1rem" }}>
            <div className="form-outline">
              <label className="form-label" for="textAreaExample">Phone Number</label>
              <textarea className="form-control form-control-sm" id="textAreaExample" rows="4"></textarea>
            </div>

            <div className="form-outline">
              <label className="form-label" for="formControlSm">Load Contact File(Excel Format)</label>
              <input type="file" id="formControlSm" className="form-control form-control-sm" />
            </div>

            <div className="form-outline">
              <label className="form-label" for="formControlSm">From:</label>
              <select className="form-control" data-mdb-filter="true">
                <option value="test1">Test1</option>
                <option value="test2">Test2</option>
                <option value="test3">Test3</option>
                <option value="test4">Test4</option>
              </select>

            </div>


            {/*<FormGroup>
              <Label>
                From:
              </Label>
              <Autocomplete
                disablePortal
                id='combo-box'
                options={senderNames}
                size="small"
                sx={{ width: { sm: 100, md: 280 } }}
                renderInput={(params) => <TextField {...params} label="Sender Names" />}
              />
            </FormGroup> */}

            <div className="form-outline">
              <label className="form-label" for="textAreaExample">Message</label>
              <textarea className="form-control form-control-sm" id="textAreaExample" rows="4"></textarea>
              <span>{counter} characters of message 1</span>
            </div>

            <FormGroup>
              <button className='app-buttons' color='secondary' >Send</button>
            </FormGroup>
          </Form>

        </Box>
        {/* </Box> */}


      </div>


      <div className='right-side'>
        <div className='message-log-table'>
        <MDBTable className='table-stripped caption-top'>
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
