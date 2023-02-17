import { Autocomplete, Box, Container, FormGroup, InputLabel, Paper, Stack, TextField, Typography } from '@mui/material'
import React from 'react'
import { useState } from 'react'
import { Form } from 'react-bootstrap'
// import { Form } from 'react-router-dom'
import { Button, Label, Table } from 'reactstrap'
import { Pane } from 'split-pane-react'
import SplitPane from 'split-pane-react/esm/SplitPane'
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
      <div className='send-form' style={{ width: '30%' }}>

        {/* <Box sx={{ width: "30%" }}> */}
        <div className="send-headers">
          <Typography>Compose Message</Typography>
          <hr />
        </div>

        <Stack direction="row" gap={1} margin="1rem" marginTop={2}>

          <Button className='send-button'>Add Contact(s)</Button>
          <Button className='send-button'>Add Group(s)</Button>

        </Stack>
        <Box>
          <Form sx={{ padding: "2rem" }}>
            <FormGroup >
              <TextField
                id="outlined-multiline-static"
                label="Phone Number(s)"
                multiline
                rows={4}
                variant='outlined'
              />
            </FormGroup>
            <FormGroup>
              <Label>
                Load Contacts File(Excel Format):
              </Label>
              <TextField
                // label="  Load Contacts File(Excel Format):"
                type="file"
                size="small"

              />
            </FormGroup>
            <FormGroup>
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
            </FormGroup>
            <FormGroup>
              <TextField
                id="outlined"
                label="Message"
                multiline
                rows={4}
                onChange={e => setCounter(e.target.value.length)}
              />
              <span>{counter} characters of message 1</span>

            </FormGroup>
            <FormGroup>
              <Button className='send-button' >Send</Button>
            </FormGroup>
          </Form>

        </Box>
        {/* </Box> */}


      </div>


      <div className='panel-view'>
        <div className="send-headers">
          <Typography>Message Log</Typography>
          <hr />
        </div>
        <div className='panel panel-default'>
          <div className="panel-body panel-resizable" style={{ height: '50%' }}>
          </div>
          <div className="panel-footer">Messages</div>

        </div>

        <div className="panel panel-default">
          <div className="panel-body panel-resizable" style={{ height: '50%' }}>

            <table className='message-log-table'>
              <thead>
                <tr>
                  <th>No</th>
                  <th>From</th>
                  <th>Message</th>
                  <th>Created</th>
                  <th>Queued</th>
                  <th>Delivered</th>
                  <th>UnDelivered</th>
                  <th>Unsent</th>
                  <th>Total</th>
                  <th>Progress</th>
                  <th>Credits</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td>1</td>
                  <td>Test</td>
                  <td>Hello</td>
                  <td>12 feb 2023 09:08AM</td>
                  <td>0</td>
                  <td>1</td>
                  <td>4</td>
                  <td>1</td>
                  <td>5</td>
                  <td>100%</td>
                  <td>1.00</td>
                </tr>
                <tr>
                  <td>1</td>
                  <td>Test</td>
                  <td>Hello</td>
                  <td>12 feb 2023 09:08AM</td>
                  <td>0</td>
                  <td>1</td>
                  <td>4</td>
                  <td>1</td>
                  <td>5</td>
                  <td>100%</td>
                  <td>1.00</td>
                </tr>
              </tbody>
            </table>


          </div>
          <div className="panel-footer">Message Log</div>

        </div>
      </div>

    </div>
  )
}

export default Send
