import { useEffect, useState } from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TablePagination from '@mui/material/TablePagination';
import TableRow from '@mui/material/TableRow';
import { API_URL } from '../../../../../Constants/Index';



const columns = [

    {
        id: 'Sender',
        label: 'From',
        minWidth: 80,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'message',
        label: 'Message ',
        minWidth: 50,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'created',
        label: 'Created',
        minWidth: 80,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'phone',
        label: 'Mobile Phone',
        minWidth: 0,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'queued',
        label: 'Queued',
        minWidth: 20,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'delivered',
        label: 'Delivered',
        minWidth: 20,
        align: 'left',
        // format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'undelivered',
        label: 'Undelivered',
        minWidth: 20,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'unsent',
        label: 'Unsent',
        minWidth: 20,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'total',
        label: 'Total',
        minWidth: 20,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'progress',
        label: 'Progress',
        minWidth: 20,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
    {
        id: 'credits',
        label: 'Credits',
        minWidth: 20,
        align: 'left',
        format: (value) => value.toLocaleString('en-US'),
    },
];

const MessageLogSummary = ({ token }) => {

    const [messageLog, setMessageLog] = useState();
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    // const [open, setOpen] = React.useState(false)
    // const handleOpen = () => setOpen(true)
    // const handleClose = () => setOpen(false)


    const [show, setShow] = useState(false)

    const handleClose = () => setShow(false)
    const handleShow = () => setShow(true)

    const handleChangePage = (event, newPage) => {
        setPage(newPage);
    };

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(+event.target.value);
        setPage(0);
    };

    var formdata = new FormData();
    formdata.append("start", "134394305");
    formdata.append("limit", "174384938");

    var requestOptions = {
        method: 'POST',
        headers: {
            'Authorization': `Token ${token}`
        },
        body: formdata,
        redirect: 'follow'
    };

    useEffect(() => {
        fetch(API_URL + "appgetmessagelogsummary", requestOptions)
            .then(response => response.json())
            .then(result => setMessageLog(convertToJsonList(result)))
            .catch(error => console.log('error', error));
    })


    function convertToJsonList(response) {
        let json_string = JSON.stringify(response[0].replace('success', '\"success\"'));
        // console.log(json_string);
        return JSON.parse(json_string);

    }

    // alert(messageLog);

    return (
        <div>
            <span>MessageLogSummary</span>
            {messageLog && <span>messageLog.total</span>}

            <TableContainer sx={{ maxHeight: 440 }}>
                <Table stickyHeader aria-label="sticky table">
                    <TableHead className='my-clients-table'>

                        <TableRow>
                            {columns.map((column) => (
                                <TableCell
                                    key={column.id}
                                    align={column.align}
                                    style={{ top: 5, minWidth: column.minWidth }}
                                >
                                    {column.label}
                                </TableCell>
                            ))}
                        </TableRow>
                    </TableHead>

                    <TableBody>
                        {messageLog &&
                            messageLog.response .map((messageSummary) => {
                                // .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                               
                                    return (
                                        <TableRow hover role="checkbox" tabIndex={-1} key={messageSummary.pk}>
                                            <TableCell>{messageSummary.sender}</TableCell>
                                            <TableCell>{messageSummary.text}</TableCell>
                                            <TableCell>{messageSummary.created[0]}</TableCell>
                                            <TableCell>{messageSummary.queued}</TableCell>
                                            <TableCell>{messageSummary.delivered}</TableCell>
                                            <TableCell>{messageSummary.undelivered}</TableCell>
                                            <TableCell>{messageSummary.unsent}</TableCell>
                                            <TableCell>{messageSummary.total}</TableCell>
                                            <TableCell>{messageSummary.progress}</TableCell>
                                            <TableCell>{messageSummary.smscredits}</TableCell>


                                        </TableRow>
                                    )
                                })}
                        </TableBody>
                </Table>
            </TableContainer>
            <TablePagination
                rowsPerPageOptions={[10, 25, 100]}
                component="div"
                count={messageLog && messageLog.total}
                rowsPerPage={rowsPerPage}
                page={page}
                onPageChange={handleChangePage}
                onRowsPerPageChange={handleChangeRowsPerPage}
            />


        </div>

    )
}

export default MessageLogSummary