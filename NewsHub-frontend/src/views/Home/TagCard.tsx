import React from 'react';
import { Button, Card, Col, Popconfirm, Typography } from "antd";
import { DeleteOutlined, EditOutlined } from '@ant-design/icons';
import { Link } from "react-router-dom";
import { ITagItem } from "../../components/tag/type";

const { Title } = Typography; 

interface ITagCardProps {
    item: ITagItem,
    handleDelete: (id: number) => void
}

const TagCard: React.FC<ITagCardProps> = ({ item, handleDelete }) => {
    const { id, name } = item;

    return (
        <Col style={{ padding: 10 }} xxl={4} lg={6} md={8} sm={12}>
            <Card
                bodyStyle={{ flex: '1', paddingBlock: '10px' }}
                style={{ height: '100%', display: 'flex', flexDirection: 'column' }}
                hoverable
                actions={[
                    <Link to={`/tag/edit/${id}`}>
                        <Button type="primary" icon={<EditOutlined />}>
                            Змінить
                        </Button>
                    </Link>,

                    <Popconfirm
                        title="Are you sure to delete this tag?"
                        onConfirm={() => handleDelete(id)}
                        okText="Yes"
                        cancelText="No"
                    >
                       <Button
                            type="primary" 
                            danger 
                            icon={<DeleteOutlined />}
                        >
                            Delete
                        </Button>
                    </Popconfirm>
                ]}
            >
                <Title level={4}>{name}</Title> 
            </Card>
        </Col>
    );
}

export default TagCard;
