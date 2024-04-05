import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { ITagEdit } from "../type"; // Припускаємо, що у вас є такий тип
import { Button, Form, Input, Row } from "antd";
import { Link } from "react-router-dom";
import http_common from "../../../http_common";

const TagEditPage = () => {
    const navigate = useNavigate();
    const { id: stringId } = useParams();
    const id = stringId ? parseInt(stringId, 10) : 0; 
    const [form] = Form.useForm<ITagEdit>();
    const [loading, setLoading] = useState(true);
    
    const loadTagData = async () => {
        if (typeof id === "number") {
            try {
                const response = await http_common.get(`/api/tags/${id}`);
                form.setFieldsValue({
                    id,
                    name: response.data.name,
                });
                setLoading(false);
            } catch (error) {
                console.error("Помилка завантаження даних тегу:", error);
            }
        }
    };

    useEffect(() => {
        loadTagData();
    }, [id]);

    const onHandlerSubmit = async (values: ITagEdit) => {
        try {
            console.log(values.id)
            await http_common.put(`/api/tags/${id}`, JSON.stringify(values), {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
    
            navigate('/tag');
        } catch (ex) {
            console.error("Помилка редагування тегу:", ex);
        }
    };

    if (loading) {
        return <p>Завантаження...</p>;
    }

    return (
        <>
            <h1>Редагувати тег</h1>
            <Row justify="center">
                <Form
                    form={form}
                    onFinish={onHandlerSubmit}
                    layout="vertical"
                    style={{ maxWidth: 600 }}
                >

                    <Form.Item name="id" hidden >
                        <Input />
                    </Form.Item>

                    <Form.Item
                        label="Назва"
                        name="name"
                        rules={[
                            { required: true, message: "Це поле є обов'язковим!" },
                            { min: 3, message: "Довжина поля має бути не менше 3 символів" }
                        ]}
                    >
                        <Input autoComplete="off" />
                    </Form.Item>

                    <Row justify="center">
                        <Button type="primary" htmlType="submit" style={{ margin: "10px" }}>
                            Оновити
                        </Button>
                        <Link to="/tags">
                            <Button style={{ margin: "10px" }}>
                                Скасувати
                            </Button>
                        </Link>
                    </Row>
                </Form>
            </Row>
        </>
    );
};

export default TagEditPage;
